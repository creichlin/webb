package ch.kerbtier.webb.dispatcher.tests;

import ch.kerbtier.webb.dispatcher.Action;
import ch.kerbtier.webb.dispatcher.Dispatcher;

@Dispatcher(pattern = "/part1/")
public class Dis3 {

  
  @Action(pattern="aaa/bbb")
  public void aaaBbb() {
    
  }
  
  @Action(pattern="a*")
  public void aaaaaa() {
    
  }

  
  @Action(pattern="bb/(.*?)/(.*?)")
  public void bb(int p1, String bla) {
    System.out.println("im bb: " + p1 + " " + bla);
  }

  @Action(pattern="b2/(.*?)/(.*?)")
  public void bb(Integer p1, String bla) {
    System.out.println("im bb: " + p1 + " " + bla);
  }

}
