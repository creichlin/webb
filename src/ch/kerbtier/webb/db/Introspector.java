package ch.kerbtier.webb.db;

import java.lang.reflect.Field;

import com.google.common.base.CaseFormat;

public class Introspector {
  
  public static String getTableName(Object obi) {
    return getTableName(obi.getClass());
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static String getTableName(Class cls) {
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
  
  public static String getColumnName(Field field) {
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
