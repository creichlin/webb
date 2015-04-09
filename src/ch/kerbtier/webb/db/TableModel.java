package ch.kerbtier.webb.db;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TableModel<T> implements Iterable<ColumnModel<T>> {
  private List<ColumnModel<T>> all = new ArrayList<ColumnModel<T>>();
  private List<ColumnModel<T>> keys = new ArrayList<ColumnModel<T>>();
  private List<ColumnModel<T>> columns = new ArrayList<ColumnModel<T>>();
  private String name;
  
  public TableModel(Class<T> type) {
    name = Introspector.getTableName(type);
    Class<?> current = type;
    
    while (current != null) {
      for (Field field : current.getDeclaredFields()) {
        Column a = field.getAnnotation(Column.class);
        if (a != null) {
          field.setAccessible(true);
          ColumnModel<T> cm = new ColumnModel<T>(this, field, a.key());
          if (String.class.isAssignableFrom(field.getType())) {
            cm.setType(String.class);
          } else if (Integer.TYPE.isAssignableFrom(field.getType()) || Integer.class.isAssignableFrom(field.getType())) {
            cm.setType(Integer.class);
          } else if (Long.TYPE.isAssignableFrom(field.getType()) || Long.class.isAssignableFrom(field.getType())) {
            cm.setType(Long.class);
          } else if (Date.class.isAssignableFrom(field.getType())) {
            cm.setType(Date.class);
          } else if (BigDecimal.class.isAssignableFrom(field.getType())) {
            cm.setType(BigDecimal.class);
          }
          
          all.add(cm);
          
          if(a.key()) {
            keys.add(cm);
          } else {
            columns.add(cm);
          }
        }
      }
      current = current.getSuperclass();
    }
  }

  @Override
  public Iterator<ColumnModel<T>> iterator() {
    return all.iterator();
  }

  public String getName() {
    return name;
  }

  public Iterable<ColumnModel<T>> columns() {
    return columns;
  }

  public Iterable<ColumnModel<T>> keys() {
    return keys;
  }

  public int keysCount() {
    return keys.size();
  }
}
