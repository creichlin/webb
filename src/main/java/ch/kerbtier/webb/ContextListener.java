package ch.kerbtier.webb;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ch.kerbtier.esdi.Esdi;
import ch.kerbtier.webb.container.ServletContextInfo;
import ch.kerbtier.webb.di.InjectSingleton;
import ch.kerbtier.webb.util.ContextInfo;

public class ContextListener implements ServletContextListener {

  public static ContainerSetup containerSetup = new ContainerSetup();

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    containerSetup.init(sce.getServletContext().getInitParameter("livecycles"));

    Esdi.onRequestFor(ContextInfo.class).with(InjectSingleton.class)
        .deliverInstance(new ServletContextInfo(sce.getServletContext()));

    containerSetup.initializedContext();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    containerSetup.destroyedContext();
  }
}
