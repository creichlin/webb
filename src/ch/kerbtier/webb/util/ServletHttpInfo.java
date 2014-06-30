package ch.kerbtier.webb.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

public class ServletHttpInfo {

  private HttpServletRequest request;
  
  public ServletHttpInfo(HttpServletRequest request) {
    this.request = request;
  }

  /**
   * returns the decoded path minus context path with no / in front
   * it does not depend on the url-mapping in the servlet, will always return the same path unlike getPathInfo
   * @return
   */
  public String getPath() {
    
    // we have to calculate path minus contextPath ourselves
    // pathInfo returns null for default servlet
    // and if servlet mapping is a path this part would be excluded
    
    String path = request.getRequestURI();
    String contextPath = request.getContextPath();
    
    path = path.substring(contextPath.length());
    
    // remove / prefix if there
    if (path.startsWith("/")) {
      path = path.substring(1);
    }
    
    try {
      path = URLDecoder.decode(path, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    
    return path;
  }
}
