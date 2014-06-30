package ch.kerbtier.webb.embeded;

import java.io.File;
import java.io.IOException;

import ch.kerbtier.webb.util.ContextInfo;

public class EmbededContextInfo implements ContextInfo {
  private String name;

  public EmbededContextInfo(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getTemplatePath() {
    try {
      return new File("templates").getCanonicalPath().toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getPath() {
    return "";
  }

}
