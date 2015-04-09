package ch.kerbtier.webb.dispatcher.tests;

import ch.kerbtier.webb.dispatcher.Call;
import ch.kerbtier.webb.dispatcher.Dispatchers;
import ch.kerbtier.webb.util.HTTPMethod;

public class Test {
  public static void main(String[] args) {
    Dispatchers dispatchers = new Dispatchers();
    
    dispatchers.register(Dis3.class);
    dispatchers.register(Dis1.class);
    dispatchers.register(Dis2.class);
    
    Call c1 = dispatchers.getCall("lala", HTTPMethod.GET).get(0);
    System.out.println(c1);
    c1.execute();
    Call c2 = dispatchers.getCall("/part1/aaa", HTTPMethod.GET).get(0);
    System.out.println(c2);
    c2.execute();
    Call c3 = dispatchers.getCall("/part1/bb/12/fdfds", HTTPMethod.GET).get(0);
    System.out.println(c3);
    c3.execute();
    Call c4 = dispatchers.getCall("/part1/b2/12/fdfds", HTTPMethod.GET).get(0);
    System.out.println(c4);
    c4.execute();
  }
}
