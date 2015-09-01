package ch.kerbtier.webb;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

  @Override
  public void sessionCreated(HttpSessionEvent hse) {

    ContextListener.containerSetup.createdSession(hse.getSession());

  }

  @Override
  public void sessionDestroyed(HttpSessionEvent hse) {

    ContextListener.containerSetup.destroyedSession(hse.getSession());

  }

}
