package ch.kerbtier.webb.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Configuration {

  private Properties properties = null;

  public Configuration(ContextInfo contextInfo) {
    String cname = contextInfo.getName();

    cname = "/etc/webb/" + cname + ".properties";

    File file = new File(cname);
    try {
      FileInputStream fileInput = new FileInputStream(file);
      properties = new Properties();
      properties.load(fileInput);
      fileInput.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  public String get(String key, String def) {
    return properties.getProperty(key, def);
  }

}
