package ch.kerbtier.webb.tests.db.util;

import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.db.DbPs;

public class Util {
  private static int counter;

  public static Db create(Object context, String sql) {

    Db db = new Db("jdbc:h2:mem:name" + (counter++) + ";USER=test;PASSWORD=test");

    URL url = Resources.getResource(context.getClass(), sql);
    try {
      String code = Resources.toString(url, Charsets.UTF_8);

      DbPs ps = db.prepareStatement(code);
      ps.executeUpdate();
      db.commit();
    } catch (Exception e) {
      db.rollback();
      throw new RuntimeException(e);
    }

    return db;
  }
}
