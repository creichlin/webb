package ch.kerbtier.webb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    mysql = new MysqlDataSource();
    mysql.setURL(url);
    Map<String, String> cpc = new HashMap<String, String>();
    cpc.put("maxIdleTime", "10");
    cpc.put("maxConnectionAge", "60");
    cpc.put("testConnectionOnCheckout", "true");

    cpc.put("debugUnreturnedConnectionStackTraces", "true");
    cpc.put("unreturnedConnectionTimeout", "30");

    try {
      ds = DataSources.pooledDataSource(mysql, cpc);
    } catch (Exception e) {
      e.printStackTrace();
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
        e.printStackTrace();
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
    try {
      statements++;
      PreparedStatement ps = getConnection().prepareStatement(sql);
      return new DbPs(ps, sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String printInfo() {
    return "JdbcInfos:statements = " + statements + ", commits = " + commits
        + ", rollbacks = " + rollbacks + "]";
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
}
