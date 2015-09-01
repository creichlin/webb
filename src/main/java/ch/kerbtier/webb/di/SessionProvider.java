package ch.kerbtier.webb.di;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import ch.kerbtier.esdi.Configuration;
import ch.kerbtier.esdi.Provider;
import ch.kerbtier.esdi.providers.SingletonProvider;

public class SessionProvider implements Provider {
  private ThreadLocal<String> sessionKey = new ThreadLocal<>();

  private Map<String, SingletonProvider> sessionMap = new HashMap<>();

  @Override
  public Object get(Configuration configuration, Annotation annotation) {
    synchronized (sessionMap) {
      SingletonProvider sp = sessionMap.get(sessionKey.get());
      if (sp == null) {
        sp = new SingletonProvider();
        sessionMap.put(sessionKey.get(), sp);
      }
      return sp.get(configuration, annotation);
    }
  }

  public void setCurrentKey(String key) {
    sessionKey.set(key);
  }

  @Override
  public void clear() {
    synchronized (sessionMap) {
      sessionMap.remove(sessionKey.get());
    }
  }
}
