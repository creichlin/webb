package ch.kerbtier.webb.util;

import java.nio.file.Path;

public interface ContextInfo {
  String getName();

  Path getLocalPath();

  String getPath();

  String getParameter(String string);
  
  String getVersion();
}
