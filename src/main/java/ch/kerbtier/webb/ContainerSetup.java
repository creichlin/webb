package ch.kerbtier.webb;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import ch.kerbtier.esdi.Esdi;
import ch.kerbtier.esdi.providers.SingletonProvider;
import ch.kerbtier.esdi.providers.ThreadLocalProvider;
import ch.kerbtier.webb.di.InjectRequest;
import ch.kerbtier.webb.di.InjectSession;
import ch.kerbtier.webb.di.InjectSingleton;
import ch.kerbtier.webb.di.InjectThreadLocal;
import ch.kerbtier.webb.di.SessionProvider;

public class ContainerSetup {

  private SingletonProvider singletonProvider = new SingletonProvider();
  private ThreadLocalProvider threadLocalProvider = new ThreadLocalProvider();
  private SessionProvider sessionProvider = new SessionProvider();
  private ThreadLocalProvider requestProvider = new ThreadLocalProvider();
  
  private ThreadLocal<ServletRequest> servletRequest = new ThreadLocal<>();
  private ThreadLocal<ServletResponse> servletResponse = new ThreadLocal<>();
  private ThreadLocal<HttpSession> httpSession = new ThreadLocal<>();

  public void init(String livecycles) {
    Esdi.register(InjectSingleton.class, singletonProvider);
    Esdi.register(InjectThreadLocal.class, threadLocalProvider);
    Esdi.register(InjectRequest.class, requestProvider);
    Esdi.register(InjectSession.class, sessionProvider);

    Esdi.onRequestFor(ServletRequest.class).with(InjectRequest.class).deliverThreadLocal(servletRequest);
    Esdi.onRequestFor(ServletResponse.class).with(InjectRequest.class).deliverThreadLocal(servletResponse);
    Esdi.onRequestFor(HttpSession.class).with(InjectSession.class).deliverThreadLocal(httpSession);

    try {
      Class<?> livecyclesClass = Class.forName(livecycles);
      if (!Livecycles.class.isAssignableFrom(livecyclesClass)) {
        throw new RuntimeException("livecycles instance must implement ch.kerbtier.webb.Livecycles");
      }
      Esdi.onRequestFor(Livecycles.class).with(InjectSingleton.class).deliver(livecyclesClass);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void initializedContext() {
    Esdi.get(Livecycles.class, InjectSingleton.class).startContext();
  }

  public void destroyedContext() {
    Esdi.get(Livecycles.class, InjectSingleton.class).stopContext();
    Esdi.clear();
  }

  public void createdSession(HttpSession session) {
    httpSession.set(session);
    sessionProvider.setCurrentKey(session.getId());
    Esdi.get(Livecycles.class, InjectSingleton.class).startSession();
  }

  public void destroyedSession(HttpSession session) {
    sessionProvider.setCurrentKey(session.getId());
    Esdi.get(Livecycles.class, InjectSingleton.class).stopSession();
    sessionProvider.clear();
  }

  public void createdRequest(ServletRequest req, ServletResponse resp) {
    servletRequest.set(req);
    servletResponse.set(resp);
    
    Esdi.get(Livecycles.class, InjectSingleton.class).startRequest();
  }

  public void destroyRequest() {
    Esdi.get(Livecycles.class, InjectSingleton.class).stopRequest();
    requestProvider.clear();
  }
}
