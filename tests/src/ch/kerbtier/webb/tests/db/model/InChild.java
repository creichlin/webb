package ch.kerbtier.webb.tests.db.model;

import ch.kerbtier.webb.db.Column;
import ch.kerbtier.webb.db.Table;

@Table
public class InChild extends InBase {
  @Column
  private String value2;

  public InChild(String value) {
    super(value);
    this.value2 = value + ":2";
  }

  public InChild() {

  }

  public String getValue2() {
    return value2;
  }

  public void setValue2(String value2) {
    this.value2 = value2;
  }
}
