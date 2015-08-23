package ch.kerbtier.webb.dispatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.kerbtier.webb.util.HTTPMethod;

public class Dispatchers {
  private List<DispatcherMatcher> dispatchers = new ArrayList<>();

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

  public List<Call> getCall(String path, HTTPMethod method) {
    List<Call> calls = new ArrayList<>();
    
    for (DispatcherMatcher dm : dispatchers) {
      calls.addAll(dm.getCall(path, method));
    }
    
    Collections.sort(calls, new Comparator<Call>() {

      @Override
      public int compare(Call o1, Call o2) {
        return Integer.compare(o1.getTotalMatched(), o2.getTotalMatched());
      }
    });
    
    return calls;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(DispatcherMatcher dm: dispatchers) {
      sb.append(dm.toString());
    }
    return sb.toString();
  }
}
