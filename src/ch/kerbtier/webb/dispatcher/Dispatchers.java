package ch.kerbtier.webb.dispatcher;

import java.util.ArrayList;
import java.util.List;

public class Dispatchers {
  private List<DispatcherMatcher> dispatchers = new ArrayList<DispatcherMatcher>();

  public void register(Class<? extends Object> def) {
    try {
      DispatcherMatcher dispatcher = new DispatcherMatcher(def.newInstance());
      dispatchers.add(dispatcher);
    } catch (IllegalAccessException iae) {
      throw new RuntimeException(iae);
    } catch (InstantiationException ie) {
      throw new RuntimeException(ie);
    }
  }

  public List<Call> getCall(String path) {
    List<Call> calls = new ArrayList<Call>();
    
    for (DispatcherMatcher dm : dispatchers) {
      calls.addAll(dm.getCall(path));
    }
    return calls;
  }

}
