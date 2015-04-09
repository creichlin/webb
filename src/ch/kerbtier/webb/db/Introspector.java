package ch.kerbtier.webb.db;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.CaseFormat;

public class Introspector {
  private static Object lock = new Object();
  private static Map<Class<?>, TableModel<?>> models = new HashMap<Class<?>, TableModel<?>>();;
  
  @SuppressWarnings("unchecked")
  public static <T extends Object> TableModel<T> getModel(T obj) {
    return (TableModel<T>) getModel(obj.getClass());
  }
  
  @SuppressWarnings("unchecked")
  public static <T extends Object> TableModel<T> getModel(Class<T> cls) {
    if(!models.containsKey(cls)) {
      synchronized(lock) {
        if(!models.containsKey(cls)) {
          models.put(cls, new TableModel<T>(cls));
        }
      }
    }
    return (TableModel<T>) models.get(cls);
  }
  
  
  
  
  public static String getTableName(Object obi) {
    return getTableName(obi.getClass());
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected static String getTableName(Class cls) {
    Table annotation = ((Class<Object>)cls).getAnnotation(Table.class);
    String name = null;
    
    if (annotation != null) {
      name = annotation.name();
      if (name.equals(Table.NULL)) {
        name = null;
      }
    }

    if (name == null) {
      name = cls.getName();
      name = name.substring(name.lastIndexOf(".") + 1);
      name = name.substring(name.lastIndexOf("$") + 1);
      name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }
    
    return name;
  }
  
  protected static String getColumnName(Field field) {
    Column annotation = field.getAnnotation(Column.class);
    String name = null;
    
    if (annotation != null) {
      name = annotation.name();
      if (name.equals(Column.NULL)) {
        name = null;
      }
    }

    if (name == null) {
      name = field.getName();
      name = name.substring(name.lastIndexOf(".") + 1);
      name = name.substring(name.lastIndexOf("$") + 1);
      name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }
    
    return name;
  }
  
  
}
