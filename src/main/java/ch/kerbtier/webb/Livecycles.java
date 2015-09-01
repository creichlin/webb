package ch.kerbtier.webb;


public interface Livecycles {

  void startContext();
  void stopContext();
  
  void startSession();
  void stopSession();
  
  void startRequest();
  void stopRequest();
  
  void request();
  
}
