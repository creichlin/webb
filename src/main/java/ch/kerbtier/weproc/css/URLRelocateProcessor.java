package ch.kerbtier.weproc.css;

import java.nio.file.Path;

import ch.kerbtier.weproc.urlmodifiers.URLRelocator;

public class URLRelocateProcessor extends URLProcessor<URLRelocator> {

  public URLRelocateProcessor() {
    super(new URLRelocator());
  }
  
  public void setBasePath(Path path){
    getModifier().setBasePath(path);
  }
  
  public void setTargetPath(Path path){
    getModifier().setTargetPath(path);
  }

}
