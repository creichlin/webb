package ch.kerbtier.webb;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.picocontainer.DefaultPicoContainer;

public class SessionListener implements HttpSessionListener {

  @Override
  public void sessionCreated(HttpSessionEvent hse) {
    DefaultPicoContainer contextContainer = (DefaultPicoContainer) hse
        .getSession().getServletContext().getAttribute("contextContainer");

    DefaultPicoContainer sessionContainer = new DefaultPicoContainer(contextContainer);
    
    ContextListener.containerSetup.createdSession(sessionContainer);
    
    hse.getSession().setAttribute("sessionContainer", sessionContainer);

  }

  @Override
  public void sessionDestroyed(HttpSessionEvent hse) {
    DefaultPicoContainer sessionContainer = (DefaultPicoContainer) hse
        .getSession().getAttribute("sessionContainer");
    ContextListener.containerSetup.destroyedSession(sessionContainer);
  }

}
