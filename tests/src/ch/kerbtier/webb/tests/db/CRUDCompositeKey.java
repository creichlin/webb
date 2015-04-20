package ch.kerbtier.webb.tests.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.db.exceptions.WrongNumberOfKeys;
import ch.kerbtier.webb.tests.db.model.SimpleComposite;
import ch.kerbtier.webb.tests.db.util.Util;

public class CRUDCompositeKey {

  private Db db;

  @Before
  public void setup() {
    db = Util.create(this, "crudCompositeKey.sql");
  }

  @Test(expected = WrongNumberOfKeys.class)
  public void createInstanceAndTryToGetByTwoIds() throws SQLException {
    SimpleComposite dt = new SimpleComposite(1, "value");
    db.create(dt, "id1");
    db.commit();

    try {
      db.select(SimpleComposite.class, dt.getId1());
    } finally {
      db.commit();
    }
  }
  
  @Test
  public void createAndGetByWhere() throws SQLException {
    SimpleComposite dt = new SimpleComposite(1, "value");
    db.create(dt, "id1");
    
    SimpleComposite dt2 = new SimpleComposite(1, "value2");
    db.create(dt2, "id1");
    db.commit();
    
    List<SimpleComposite> dtr = db.select(SimpleComposite.class, "id1 = ? and id2 = ?", 1, dt.getId2());
    
    assertEquals(1, dtr.size());
    assertEquals("value:1", dtr.get(0).getValue1());
  }

  @Test
  public void createInstanceAndDelete() throws SQLException {
    SimpleComposite dt = new SimpleComposite(1, "value");
    db.create(dt, "id1");
    db.commit();

    assertEquals(1, db.count(SimpleComposite.class));

    db.delete(dt);

    assertEquals(0, db.count(SimpleComposite.class));
    db.commit();
  }

  @Test
  public void createAndUpdateSameObject() throws SQLException {
    SimpleComposite dt = new SimpleComposite(1, "value");
    db.create(dt, "id1");
    db.commit();
    
    dt.setValue1("rhino");
    db.update(dt);
    db.commit();
    assertEquals("rhino", db.selectFirst(SimpleComposite.class, "id1 = ? AND id2 = ?", dt.getId1(), dt.getId2()).getValue1());
    db.commit();
  }

  @Test
  public void createAndUpdateDifferentObject() throws SQLException {
    SimpleComposite dt = new SimpleComposite(1, "value");
    db.create(dt, "id1");
    db.commit();
    
    SimpleComposite other = db.selectFirst(SimpleComposite.class, "id1 = ? AND id2 = ?", dt.getId1(), dt.getId2());
    other.setValue1("rhino");
    db.update(other);
    db.commit();
    assertEquals("rhino", db.selectFirst(SimpleComposite.class, "id1 = ? AND id2 = ?", dt.getId1(), dt.getId2()).getValue1());
    db.commit();
  }

  @Test
  public void createAndSelectAll() throws SQLException {
    db.create(new SimpleComposite(1, "valueA"), "id1");
    db.create(new SimpleComposite(1, "valueB"), "id1");
    db.create(new SimpleComposite(1, "valueC"), "id1");
    db.create(new SimpleComposite(1, "valueD"), "id1");
    db.commit();
    
    List<SimpleComposite> all = db.select(SimpleComposite.class);
    assertEquals(4, all.size());
    db.commit();
  }
}
