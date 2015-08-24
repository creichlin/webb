package ch.kerbtier.webb.container;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import ch.kerbtier.webb.util.ContextInfo;

public class ServletContextInfo implements ContextInfo {
  private ServletContext subject;
  
  public ServletContextInfo(ServletContext subject) {
    this.subject = subject;
  }

  @Override
  public String getName() {
    String cname = subject.getContextPath();

    if (cname.length() == 0) {
      cname = "default";
    } else {
      cname = cname.substring(1);
    }
    return cname;
  }

  @Override
  public String getPath() {
    return subject.getContextPath();
  }

  @Override
  public Path getLocalPath() {
    return Paths.get(subject.getRealPath(".")).toAbsolutePath().normalize();
  }

  @Override
  public String getParameter(String name) {
    return subject.getInitParameter(name);
  }

  @Override
  public String getVersion() {
    return subject.getMajorVersion() + "." + subject.getMinorVersion();
  }
}
