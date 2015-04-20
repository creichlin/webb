package ch.kerbtier.webb.db.exceptions;

public class WrongNumberOfKeys extends RuntimeException {
  public WrongNumberOfKeys(String desc) {
    super(desc);
  }
}
