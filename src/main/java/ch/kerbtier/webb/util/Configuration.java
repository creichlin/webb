package ch.kerbtier.webb.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import ch.kerbtier.esdi.Inject;
import ch.kerbtier.webb.di.InjectSingleton;

@Inject
public class Configuration {

  private Properties properties = null;
  
  @InjectSingleton
  private ContextInfo contextInfo;

  public Configuration() {
    String configName = contextInfo.getParameter("config-name");
    String cname = contextInfo.getName();
    
    if(configName != null) {
      cname = configName + "." + cname;
    }

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

  public boolean get(String key, boolean def) {
    String value = get(key, "" + def);
    
    if("true".equals(value) || "yes".equals(value)) {
      return true;
    }
    
    return false;
  }

}
