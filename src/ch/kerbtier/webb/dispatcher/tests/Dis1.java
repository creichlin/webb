package ch.kerbtier.webb.dispatcher.tests;

import ch.kerbtier.webb.dispatcher.Action;
import ch.kerbtier.webb.dispatcher.Dispatcher;

@Dispatcher(pattern = "/oy")
public class Dis1 {

  @Action
  public void lala() {
    
  }
  
}
