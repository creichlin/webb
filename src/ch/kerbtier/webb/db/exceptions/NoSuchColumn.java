package ch.kerbtier.webb.db.exceptions;

public class NoSuchColumn extends RuntimeException {
  public NoSuchColumn(String desc) {
    super(desc);
  }
}
