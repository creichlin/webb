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
    contextContainer.getComponent(Livecycles.class).startContext(contextContainer);
    contextContainer.start();
  }

  public void destroyedContext(DefaultPicoContainer contextContainer) {
    contextContainer.stop();
    contextContainer.getComponent(Livecycles.class).stopContext(contextContainer);
    contextContainer.dispose();
  }

  public void createdSession(DefaultPicoContainer sessionContainer) {
    sessionContainer.getComponent(Livecycles.class).startSession(sessionContainer);
    sessionContainer.start();

    System.out.println("session setp");
  }

  public void destroyedSession(DefaultPicoContainer sessionContainer) {
    sessionContainer.stop();
    sessionContainer.getComponent(Livecycles.class).stopSession(sessionContainer);
  }

  public void createdRequest(DefaultPicoContainer requestContainer, ServletRequest req, ServletResponse resp) {
    requestContainer.getComponent(Livecycles.class).startRequest(requestContainer);
    
    requestContainer.addComponent(req);
    requestContainer.addComponent(resp);
    
    requestContainer.start();
    
    System.out.println("request setp");
  }

  public void destroyRequest(DefaultPicoContainer requestContainer) {
    requestContainer.stop();

    requestContainer.getComponent(Livecycles.class).stopRequest(
        requestContainer);
  }

}
