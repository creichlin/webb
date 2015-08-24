package ch.kerbtier.webb;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.picocontainer.DefaultPicoContainer;

import ch.kerbtier.webb.container.ServletContextInfo;

public class ContextListener implements ServletContextListener {
  
  public static ContainerSetup containerSetup = new ContainerSetup();
  
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    
    DefaultPicoContainer contextContainer = new DefaultPicoContainer();
    containerSetup.initializedContext(contextContainer, new ServletContextInfo(sce.getServletContext()), sce.getServletContext().getInitParameter("livecycles"));
    sce.getServletContext().setAttribute("contextContainer", contextContainer);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    DefaultPicoContainer contextContainer = (DefaultPicoContainer) sce
        .getServletContext().getAttribute("contextContainer");

    containerSetup.destroyedContext(contextContainer);
  }
}
