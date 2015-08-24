package ch.kerbtier.weproc.css;

import java.io.File;

public interface LessCssPathResolver {
  File getFile(String path); 
}
