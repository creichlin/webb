package ch.kerbtier.webb;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.picocontainer.DefaultPicoContainer;

public class RequestFilter implements Filter {

  @Override
  public void destroy() {

  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain chain) throws IOException, ServletException {

    DefaultPicoContainer sessionContainer = (DefaultPicoContainer) ((HttpServletRequest) req)
        .getSession().getAttribute("sessionContainer");

    DefaultPicoContainer requestContainer = new DefaultPicoContainer(
        sessionContainer);

    req.setAttribute("requestContainer", requestContainer);

    ContextListener.containerSetup.createdRequest(requestContainer, req, resp);

    chain.doFilter(req, resp);
    
    ContextListener.containerSetup.destroyRequest(requestContainer);
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {

  }

}
