package ch.kerbtier.webb.embeded;

import java.nio.file.Path;
import java.nio.file.Paths;

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
  public String getPath() {
    return "";
  }

  @Override
  public Path getLocalPath() {
    return Paths.get(".").toAbsolutePath();
  }

  @Override
  public String getParameter(String string) {
    return null;
  }

}
