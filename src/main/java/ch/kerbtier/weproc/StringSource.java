package ch.kerbtier.weproc;

public class StringSource implements Source {

  private String text;
  private String name;
  
  public StringSource(String text, String name) {
    this.text = text;
    this.name = name;
  }
  
  @Override
  public String get() {
    return text;
  }

  @Override
  public String getName() {
    return name;
  }
}
