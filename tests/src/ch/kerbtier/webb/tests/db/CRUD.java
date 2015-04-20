package ch.kerbtier.webb.tests.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.tests.db.model.Simple;
import ch.kerbtier.webb.tests.db.util.Util;

public class CRUD {

  private Db db;

  @Before
  public void setup() {
    db = Util.create(this, "crud.sql");
  }

  @Test()
  public void createInstanceAndSelectById() throws SQLException {
    Simple dt = new Simple("value");
    db.create(dt);
    db.commit();

    Simple dt2 = db.select(Simple.class, dt.getId());

    assertEquals(dt.getValue1(), dt2.getValue1());
    assertEquals(1, db.count(Simple.class));
    db.commit();
  }

  @Test()
  public void createInstanceAndDelete() throws SQLException {
    Simple dt = new Simple("value");
    db.create(dt);
    db.commit();

    assertEquals(1, db.count(Simple.class));

    db.delete(dt);

    assertEquals(0, db.count(Simple.class));
    db.commit();
  }

  @Test()
  public void createAndUpdateSameObject() throws SQLException {
    Simple dt = new Simple("value");
    db.create(dt);
    db.commit();
    
    dt.setValue1("rhino");
    db.update(dt);
    db.commit();
    assertEquals("rhino", db.select(Simple.class, dt.getId()).getValue1());
    db.commit();
  }

  @Test()
  public void createAndUpdateDifferentObject() throws SQLException {
    Simple dt = new Simple("value");
    db.create(dt);
    db.commit();
    
    Simple other = db.select(Simple.class, dt.getId());
    other.setValue1("rhino");
    db.update(other);
    db.commit();
    assertEquals("rhino", db.select(Simple.class, dt.getId()).getValue1());
    db.commit();
  }

  @Test()
  public void createAndSelectAll() throws SQLException {
    db.create(new Simple("valueA"));
    db.create(new Simple("valueB"));
    db.create(new Simple("valueC"));
    db.create(new Simple("valueD"));
    db.commit();
    
    List<Simple> all = db.select(Simple.class);
    assertEquals(4, all.size());
    db.commit();
  }
}
