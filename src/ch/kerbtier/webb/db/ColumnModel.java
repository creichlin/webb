package ch.kerbtier.webb.db;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

public class ColumnModel<T> {
  private Field field;
  private Class<?> type;
  private String name;
  private boolean key;
  
  public ColumnModel(TableModel<T> tableModel, Field field, boolean key) {
    this.field = field;
    this.key = key;
    this.name = Introspector.getColumnName(field);
  }

  public void setType(Class<?> type) {
    this.type = type;
  }
  
  public boolean isKey() {
    return key;
  }
  
  public String getString(T instance) {
    if(isString()) {
      try {
        return (String)field.get(instance);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    throw new RuntimeException("wrong type");
  }

  public Long getLong(T instance) {
    if(isLong()) {
      try {
        return (Long)field.get(instance);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    throw new RuntimeException("wrong type");
  }

  public Integer getInteger(T instance) {
    if(isInteger()) {
      try {
        return (Integer)field.get(instance);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    throw new RuntimeException("wrong type");
  }

  public Date getDate(T instance) {
    if(isDate()) {
      try {
        return (Date)field.get(instance);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    throw new RuntimeException("wrong type");
  }
  
  public <R> R get(T instance, Class<R> pt) {
    if(is(pt)) {
      try {
        return (R)field.get(instance);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    throw new RuntimeException("wrong type");
  }

  public void set(T instance, Object value) {
    if(value == null || is(value.getClass())) {
      try {
        field.set(instance, value);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    } else {
      throw new RuntimeException("wrong type: " + value.getClass() + " for " + type);
    }
  }
  
  public boolean is(Class<?> t) {
    return type.isAssignableFrom(t);
  }

  public boolean isString() {
    return type == String.class;
  }

  public boolean isInteger() {
    return type == Integer.class;
  }

  public boolean isLong() {
    return type == Long.class;
  }

  public boolean isDate() {
    return type == Date.class;
  }

  public String getName() {
    return name;
  }
}
