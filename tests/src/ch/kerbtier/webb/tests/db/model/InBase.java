package ch.kerbtier.webb.tests.db.model;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table
public class InBase {

  @Column(key = true)
  private int identifier = -1;

  @Column
  private String value1;


  public InBase(String value) {
    this.value1 = value + ":1";
  }

  public InBase() {

  }

  public String getValue1() {
    return value1;
  }

  public void setValue1(String value1) {
    this.value1 = value1;
  }

  public int getIdentifier() {
    return identifier;
  }

}
