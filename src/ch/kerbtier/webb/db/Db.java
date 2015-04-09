package ch.kerbtier.webb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Db {
  // private static Logger logger = Logger.getLogger(Db.class.getName());
  private DataSource ds;
  private MysqlDataSource mysql;
  private ThreadLocal<Connection> connection = new ThreadLocal<Connection>();
  private int commits = 0;
  private int rollbacks = 0;
  private int statements = 0;

  public Db(String url) {
    boolean usePool = true;
    mysql = new MysqlDataSource();
    mysql.setURL(url);

    if (usePool) {
      
      Map<String, String> cpc = new HashMap<String, String>();
      cpc.put("maxIdleTime", "60");
      cpc.put("maxConnectionAge", "600");
      cpc.put("testConnectionOnCheckout", "true");

      cpc.put("debugUnreturnedConnectionStackTraces", "true");  
      

      cpc.put("unreturnedConnectionTimeout", "10");

      try {
        ds = DataSources.pooledDataSource(mysql, cpc);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {

      ds = mysql;
    }
  }

  private Connection getConnection() {
    if (connection.get() == null) {
      try {
        connection.set(ds.getConnection());

        if (connection.get().getAutoCommit() == true) {
          connection.get().setAutoCommit(false);
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    return connection.get();
  }

  public void commit() {
    try {
      Connection con = connection.get();
      if (con != null) {
        connection.set(null);
        con.commit();
        con.close();
        commits++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void rollback() {
    try {
      Connection con = connection.get();
      if (con != null) {
        connection.set(null);
        con.rollback();
        con.close();
        rollbacks++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public DbPs prepareStatement(String sql) {
    return prepareStatement(sql, false);
  }

  public DbPs prepareStatement(String sql, boolean returnKeys) {
    try {
      statements++;
      PreparedStatement ps = getConnection().prepareStatement(sql, returnKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
      return new DbPs(ps, sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public <X> X select(Class<X> type, Integer... ids) throws SQLException  {
    TableModel<?> tModel = Introspector.getModel(type);
    
    if(tModel.keysCount() != ids.length) {
      throw new RuntimeException("please provide correct number of keys as parameters. " + tModel.keysCount() + " instead of " + ids.length);
    }
    
    DbPs ps = prepareStatement("select * from `" + tModel.getName() + "` where id = ?");
    int pos = 1;
    for(int id: ids) {
      ps.setInt(pos++, id);
    }
    
    DbRs rs = ps.executeQuery();
    if(rs.next()) {
      return rs.populate(type);
    } else {
      throw new RuntimeException("no instance found for keys " + ids);
    }
  }

  public DbPs prepareDelete(Class<?> type) {
    TableModel<?> tModel = Introspector.getModel(type);
    
    if(!tModel.keys().iterator().hasNext()) {
      throw new RuntimeException("to delete entitiy it needs at least one key");
    }

    StringBuilder statemenet = new StringBuilder("DELETE FROM " + tModel.getName() + " WHERE ");

    for(ColumnModel<?> cModel: tModel.keys()) {
      statemenet.append("`" + cModel.getName() + "` = ? AND ");
    }
    statemenet.setLength(statemenet.length() - 5);
    
    System.out.println("created statemenet: " + statemenet.toString());
    return prepareStatement(statemenet.toString());
  }
  
  public void delete(Object instance) throws SQLException {
    DbPs ps = prepareDelete(instance.getClass());
    ps.setEntityKeys(instance);
    ps.executeUpdate();
  }

  public DbPs prepareUpdate(Class<?> type) {
    TableModel<?> tModel = Introspector.getModel(type);
    
    if(!tModel.keys().iterator().hasNext()) {
      throw new RuntimeException("to update entitiy it needs at least one key");
    }
    
    StringBuilder statemenet = new StringBuilder("UPDATE " + tModel.getName() + " SET ");
    
    for(ColumnModel<?> cModel: tModel.columns()) {
      statemenet.append("`" + cModel.getName() + "` = ?, ");
    }
    statemenet.setLength(statemenet.length() - 2);
    statemenet.append(" WHERE ");
    
    for(ColumnModel<?> cModel: tModel.keys()) {
      statemenet.append("`" + cModel.getName() + "` = ? AND ");
    }
    statemenet.setLength(statemenet.length() - 5);
    
    System.out.println("created statemenet: " + statemenet.toString());
    return prepareStatement(statemenet.toString());
  }
  
  public void update(Object instance) throws SQLException {
    DbPs ps = prepareUpdate(instance.getClass());
    ps.setEntity(instance);
    ps.executeUpdate();
  }

  public DbPs prepareCreate(Class<?> type) {
    TableModel<?> tModel = Introspector.getModel(type);
    
    if(!tModel.keys().iterator().hasNext()) {
      throw new RuntimeException("to update entitiy it needs at least one key");
    }
    
    StringBuilder statemenet = new StringBuilder("INSERT INTO " + tModel.getName() + " (");
    
    for(ColumnModel<?> cModel: tModel.columns()) {
      statemenet.append("`" + cModel.getName() + "`, ");
    }
    statemenet.setLength(statemenet.length() - 2);
    statemenet.append(") VALUES(");
    
    for(@SuppressWarnings("unused") ColumnModel<?> cModel: tModel.columns()) {
      statemenet.append("?, ");
    }
    statemenet.setLength(statemenet.length() - 2);
    statemenet.append(")");
    System.out.println("created statemenet: " + statemenet.toString());
    return prepareStatement(statemenet.toString(), true);
  }
  
  public void create(Object instance) throws SQLException {
    DbPs ps = prepareCreate(instance.getClass());
    ps.setEntityColumns(instance);
    ps.executeUpdate();
    
    // update ids in instance
    TableModel<Object> tModel = (TableModel<Object>)Introspector.getModel(instance.getClass());
    
    
    ResultSet rs = ps.getGeneratedKeys();
    
    for(ColumnModel<Object> key: tModel.keys()) {
      if(rs.next()) {
        key.set(instance, rs.getInt(1));
      } else {
        throw new RuntimeException("invalid amount of generated keys");
      }
    }
    
    if(rs.next()) {
      throw new RuntimeException("invalid amount of generated keys");
    }
  }
  
  

  public String printInfo() {
    return "JdbcInfos:statements = " + statements + ", commits = " + commits + ", rollbacks = " + rollbacks + "]";
  }

  public void destroy() {
    try {
      DataSources.destroy(ds);
      try {
        AbandonedConnectionCleanupThread.shutdown();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean hasOpenConnection() {
    return connection.get() != null;
  }

}
