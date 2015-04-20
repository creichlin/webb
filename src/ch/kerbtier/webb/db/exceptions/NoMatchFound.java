package ch.kerbtier.webb.db.exceptions;

public class NoMatchFound extends RuntimeException {
  public NoMatchFound(String desc) {
    super(desc);
  }
}
