package ch.kerbtier.webb.index;

import java.lang.reflect.Method;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.google.common.base.CaseFormat;

public class DocumentCreator {
  
  
  public static String createQuery(Object object) {
    Class current = object.getClass();
    StringBuilder query = new StringBuilder();
    
    while (current != null) {
      for (Method method : current.getDeclaredMethods()) {
        try {
          Indexed a = method.getAnnotation(Indexed.class);
          if (a != null) {
            method.setAccessible(true);
            if (a.type() == IndexType.ID) {
              query.append("id:" +  method.invoke(object) + " AND ");
            } else if (a.type() == IndexType.TYPE) {
              query.append("type:" +  method.invoke(object) + " AND ");
            }

          }
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
      current = current.getSuperclass();
    }
    return query.substring(0, query.length() - 5).toString();
  }

  public static Document create(Object object) {

    Document doc = new Document();

    @SuppressWarnings("rawtypes")
    Class current = object.getClass();

    while (current != null) {
      for (Method method : current.getDeclaredMethods()) {
        try {
          Indexed a = method.getAnnotation(Indexed.class);
          if (a != null) {
            method.setAccessible(true);
            String name = normalizeName(method);

            Field field = null;

            if (a.type() == IndexType.TOKEN) {
              field = new StringField(name, (String) method.invoke(object),
                  Field.Store.NO);
            } else if (a.type() == IndexType.TEXT) {
              field = new TextField(name, (String) method.invoke(object),
                  Field.Store.NO);
            } else if (a.type() == IndexType.ID) {
              field = new StringField("id", (String) method.invoke(object),
                  Field.Store.YES);
            } else if (a.type() == IndexType.TYPE) {
              field = new StringField("type", (String) method.invoke(object),
                  Field.Store.NO);
            }

            doc.add(field);
          }
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
      current = current.getSuperclass();
    }
    return doc;
  }


  private static String normalizeName(Method method) {
    String name = method.getName();
    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);

    if (name.startsWith("get_")) {
      name = name.substring(4);
    }

    if (name.startsWith("is_")) {
      name = name.substring(3);
    }
    return name;
  }
  
}
