package ch.kerbtier.webb.container;

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
  public String getTemplatePath() {
    return subject.getRealPath("templates");
  }

  @Override
  public String getPath() {
    return subject.getContextPath();
  }
}
