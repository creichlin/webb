package ch.kerbtier.webb;

import org.picocontainer.DefaultPicoContainer;

public interface Livecycles {

  void startContext(DefaultPicoContainer container);
  void stopContext(DefaultPicoContainer container);
  
  void startSession(DefaultPicoContainer container);
  void stopSession(DefaultPicoContainer container);
  
  void startRequest(DefaultPicoContainer container);
  void stopRequest(DefaultPicoContainer container);
  
  void request(DefaultPicoContainer container);
  
}
