package ch.kerbtier.webb;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;

import ch.kerbtier.webb.util.ContextInfo;

public class ContainerSetup {

  public void initializedContext(DefaultPicoContainer contextContainer, ContextInfo contextInfo, String livecycles) {
    try {
      Class<?> livecyclesClass = Class.forName(livecycles);
      if(!Livecycles.class.isAssignableFrom(livecyclesClass)) {
        throw new RuntimeException("livecycles instance must implement ch.kerbtier.webb.Livecycles");
      }
      contextContainer.as(Characteristics.CACHE).addComponent(livecyclesClass);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    
    contextContainer.addComponent(contextInfo);
    contextContainer.start();
    contextContainer.getComponent(Livecycles.class).startContext(contextContainer);
  }

  public void destroyedContext(DefaultPicoContainer contextContainer) {
    contextContainer.getComponent(Livecycles.class).stopContext(contextContainer);
    contextContainer.stop();
    contextContainer.dispose();
  }

  public void createdSession(DefaultPicoContainer sessionContainer) {
    sessionContainer.start();
    sessionContainer.getComponent(Livecycles.class).startSession(sessionContainer);
  }

  public void destroyedSession(DefaultPicoContainer sessionContainer) {
    sessionContainer.getComponent(Livecycles.class).stopSession(sessionContainer);
    sessionContainer.stop();
    sessionContainer.dispose();
  }

  public void createdRequest(DefaultPicoContainer requestContainer, ServletRequest req, ServletResponse resp) {
    requestContainer.addComponent(req);
    requestContainer.addComponent(resp);
    requestContainer.start();
    requestContainer.getComponent(Livecycles.class).startRequest(requestContainer);
  }

  public void destroyRequest(DefaultPicoContainer requestContainer) {
    requestContainer.getComponent(Livecycles.class).stopRequest(
        requestContainer);
    requestContainer.stop();
    requestContainer.dispose();
  }

}
