package ch.kerbtier.webb.db;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DbRs {
  private ResultSet rs;

  public DbRs(ResultSet rs) {
    this.rs = rs;
  }

  public <T> T populate(Class<T> type) {
    try {
      T obj = type.newInstance();

      @SuppressWarnings("rawtypes")
      Class current = type;

      while (current != null) {
        for (Field field : current.getDeclaredFields()) {
          Column a = field.getAnnotation(Column.class);
          if (a != null) {
            field.setAccessible(true);
            String columnName = Introspector.getColumnName(field);

            if (String.class.isAssignableFrom(field.getType())) {
              field.set(obj, getString(columnName));
            } else if (Integer.TYPE.isAssignableFrom(field.getType()) || Integer.class.isAssignableFrom(field.getType())) {
              field.set(obj, getInt(columnName));
            } else if (Long.TYPE.isAssignableFrom(field.getType()) || Long.class.isAssignableFrom(field.getType())) {
              field.set(obj, getInt(columnName));
            } else if (Date.class.isAssignableFrom(field.getType())) {
              field.set(obj, getDate(columnName));
            }
          }
        }
        current = current.getSuperclass();
      }

      return obj;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public boolean next() throws SQLException {
    return rs.next();
  }

  public boolean isFirst() throws SQLException {
    return rs.isFirst();
  }

  public String getString(String name) throws SQLException {
    return rs.getString(name);
  }

  public Long getLong(int i) throws SQLException {
    long l = rs.getLong(i);
    if (rs.wasNull()) {
      return null;
    }
    return l;
  }

  public Integer getInt(String name) throws SQLException {
    int ii = rs.getInt(name);
    if (rs.wasNull()) {
      return null;
    }
    return ii;
  }

  public Date getDate(String name) throws SQLException {
    return rs.getDate(name);
  }

  public Date getDateTime(String name) throws SQLException {
    return rs.getTimestamp(name);
  }

  public Long getLong(String name) throws SQLException {
    long l = rs.getLong(name);
    if (rs.wasNull()) {
      return null;
    }
    return l;
  }

  public boolean getBoolean(String name) throws SQLException {
    return rs.getBoolean(name);
  }

  public Integer getInt(int i) throws SQLException {
    int ii = rs.getInt(i);
    if (rs.wasNull()) {
      return null;
    }
    return ii;
  }

  public BigDecimal getDecimal(String name) throws SQLException {
    return rs.getBigDecimal(name);
  }
}
