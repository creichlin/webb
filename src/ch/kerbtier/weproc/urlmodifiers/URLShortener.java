package ch.kerbtier.weproc.urlmodifiers;

import java.io.File;
import java.io.IOException;

import ch.kerbtier.weproc.tools.HashShortener;

public class URLShortener implements URLModifier {

  private HashShortener minifizer = null;

  private File basePath = new File(".").getAbsoluteFile();

  private String prefix;

  public URLShortener(HashShortener minifizer, String prefix) {
    this.minifizer = minifizer;
    this.prefix = prefix;
  }

  public void setBasePath(File file) {
    this.basePath = file.getAbsoluteFile();
  }

  @Override
  public String change(String url) {

    boolean proto = url.startsWith("http://") || url.startsWith("https://") || url.startsWith("//");

    if (proto) {
      return url;
    }

    File path = new File(basePath, url);

    String ext = "png";

    try {
      String name = prefix + minifizer.getShortForm(path.getCanonicalPath());

      if (ext != null) {
        name += "." + ext;
      }

      return name;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return url;
  }
}
