package ch.kerbtier.webb.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DbPs {
	private PreparedStatement ps;
	@SuppressWarnings("unused")
	private String sql;

	public DbPs(PreparedStatement ps, String sql) {
		this.ps = ps;
		this.sql = sql;
	}

	public void setString(int i, String string) throws SQLException {
		ps.setString(i, string);
	}

	public void setDate(int i, Date date) throws SQLException {
		if (date != null) {
			ps.setDate(i, new java.sql.Date(date.getTime()));
		} else {
			ps.setDate(i, null);
		}
	}
	
	public void setDateTime(int i, Date date) throws SQLException{
		if (date != null) {
			ps.setTimestamp(i, new java.sql.Timestamp(date.getTime()));
		} else {
			ps.setDate(i, null);
		}
	}

	public int executeUpdate() throws SQLException {
		//System.out.println("UPDATE:" + sql);
		return ps.executeUpdate();
	}

	public long getGeneratedKey() throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		return rs.getLong(1);
	}

	public void setLong(int i, Long l) throws SQLException {
		if (l == null) {
			ps.setNull(i, Types.BIGINT);
		} else {
			ps.setLong(i, l);
		}
	}

	public DbRs executeQuery() throws SQLException {
		//System.out.println("QUERY:" + sql);
		return new DbRs(ps.executeQuery());
	}

	public void setInt(int i, Integer in) throws SQLException {
		if (in == null) {
			ps.setNull(i, Types.BIGINT);
		} else {
			ps.setLong(i, in);
		}
	}

	public void setBoolean(int i, boolean value) throws SQLException {
		ps.setBoolean(i, value);
	}

	public void setDecimal(int i, BigDecimal value) throws SQLException {
		ps.setBigDecimal(i, value);
	}

	public void close() throws SQLException {
		ps.close();
	}

	public int getGeneratedIntKey() throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}

  public <T> List<T> select(Class<T> type) throws SQLException {
    List<T> list = new ArrayList<T>();

    DbRs result = executeQuery();

    while (result.next()) {
      T element = result.populate(type);
      list.add(element);
    }
    return list;
  }

  public <T> List<T> selectEach(Class<T> type, List<Integer> in) throws SQLException {
    List<T> list = new ArrayList<T>();
    
    for(Integer i: in) {
      setInt(1, i);
      list.addAll(select(type));
    }
    
    return list;
  }
}
