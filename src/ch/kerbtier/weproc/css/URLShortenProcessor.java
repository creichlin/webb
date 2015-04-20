package ch.kerbtier.weproc.css;

import java.io.File;

import ch.kerbtier.weproc.tools.HashShortener;
import ch.kerbtier.weproc.urlmodifiers.URLShortener;

public class URLShortenProcessor extends URLProcessor<URLShortener> {

  public URLShortenProcessor(HashShortener shortener, File basePath, String prefix) {
    super(new URLShortener(shortener, prefix));
    setBasePath(basePath);
  }

  public void setBasePath(File file){
    getModifier().setBasePath(file);
  }

}
