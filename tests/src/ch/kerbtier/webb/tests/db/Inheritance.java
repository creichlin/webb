package ch.kerbtier.webb.tests.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.tests.db.model.InChild;
import ch.kerbtier.webb.tests.db.util.Util;

public class Inheritance {

  private Db db;

  @Before
  public void setup() {
    db = Util.create(this, "inheritance.sql");
  }

  @Test()
  public void createChildInstanceAndSelectById() throws SQLException {
    InChild dt = new InChild("value");
    db.create(dt);
    db.commit();

    InChild dt2 = db.select(InChild.class, dt.getIdentifier());

    assertEquals(dt.getValue1(), dt2.getValue1());
    assertEquals(1, db.count(InChild.class));
    db.commit();
  }

  @Test
  public void createInstanceAndDelete() throws SQLException {
    InChild dt = new InChild("value");
    db.create(dt);
    db.commit();

    assertEquals(1, db.count(InChild.class));

    db.delete(dt);

    assertEquals(0, db.count(InChild.class));
    db.commit();
  }

  @Test
  public void createAndUpdateSameObject() throws SQLException {
    InChild dt = new InChild("value");
    db.create(dt);
    db.commit();
    
    dt.setValue1("rhino");
    db.update(dt);
    db.commit();
    assertEquals("rhino", db.select(InChild.class, dt.getIdentifier()).getValue1());
    db.commit();
  }

  @Test
  public void createAndUpdateDifferentObject() throws SQLException {
    InChild dt = new InChild("value");
    db.create(dt);
    db.commit();
    
    InChild other = db.select(InChild.class, dt.getIdentifier());
    other.setValue1("rhino");
    db.update(other);
    db.commit();
    assertEquals("rhino", db.select(InChild.class, dt.getIdentifier()).getValue1());
    db.commit();
  }

  @Test
  public void createAndSelectAll() throws SQLException {
    db.create(new InChild("valueA"));
    db.create(new InChild("valueB"));
    db.create(new InChild("valueC"));
    db.create(new InChild("valueD"));
    db.commit();
    
    List<InChild> all = db.select(InChild.class);
    assertEquals(4, all.size());
    db.commit();
  }
}
