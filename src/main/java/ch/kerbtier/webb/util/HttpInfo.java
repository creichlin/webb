package ch.kerbtier.webb.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.kerbtier.esdi.Inject;
import ch.kerbtier.webb.di.InjectRequest;

@Inject
public class HttpInfo {
  
  @InjectRequest
  private HttpServletRequest request;
  
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
    
    if(contextPath != null) {
      path = path.substring(contextPath.length());
    }
    
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

  public Map<String, String[]> getParameterMap() {
    return request.getParameterMap();
  }

  /**
   * returns url with protocol, server, port, path and querystring
   * @return
   */
  public String getURL() {
      StringBuffer receivingURL = request.getRequestURL();
      String queryString = request.getQueryString();
      if (queryString != null && queryString.length() > 0) {
        receivingURL.append("?").append(queryString);
      }
      return receivingURL.toString();
    }

  public HTTPMethod getMethod() {
    return HTTPMethod.valueOf(request.getMethod());
  }

  public HttpInfoParameter getParameter(String key) {
    return new HttpInfoParameter(request.getParameterMap().get(key));
  }

  public Iterable<String> parameterNames() {
    return request.getParameterMap().keySet();
  }

  public  Map<String, String> getParameterValueMap() {
    Map<String, String> params = new HashMap<>();
    
    for(String name: parameterNames()) {
      params.put(name, getParameter(name).asString());
    }
    
    
    return params;
  }
}
