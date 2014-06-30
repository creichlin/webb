package ch.kerbtier.webb.dispatcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DispatcherMatcher {

  private String pattern;
  private List<CallMatcher> callMatchers = new ArrayList<CallMatcher>();
  
  public DispatcherMatcher(Object subject) {
    
    Dispatcher annotation = subject.getClass().getAnnotation(Dispatcher.class);
    if(annotation != null) {
      if(!annotation.pattern().equals(Dispatcher.NULL)) {
        pattern = annotation.pattern();
      }
    }
    

    for(Method method: subject.getClass().getMethods()) {
      Action action = method.getAnnotation(Action.class);
      if(action != null) {
        CallMatcher cm = new CallMatcher(pattern, subject, method, action);
        callMatchers.add(cm);
      }
    }
    
  }
  
  public List<Call> getCall(String path) {
    List<Call> calls = null;
    for(CallMatcher cm: callMatchers) {
      Call call = cm.getCall(path);
      if(call != null) {
        if(calls == null) {
          calls = new ArrayList<Call>();
        }
        calls.add(call);
      }
    }

    if(calls != null) {
      return calls;
    }
    return Collections.emptyList();
  }
}
