package ch.kerbtier.weproc.css;

import java.io.File;

import ch.kerbtier.weproc.urlmodifiers.URLHasher;
import ch.kerbtier.weproc.urlmodifiers.URLHasher.Action;

public class URLHashProcessor extends URLProcessor<URLHasher> {

  public URLHashProcessor(File basePath, Action action) {
    super(new URLHasher(basePath, action));
  }

  public void setBasePath(File file){
    getModifier().setBasePath(file);
  }
}
