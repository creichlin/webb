package ch.kerbtier.webb.dispatcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.kerbtier.webb.util.HTTPMethod;

public class DispatcherMatcher {

  private String pattern;
  private HTTPMethod httpMethod = HTTPMethod.GET;
  private List<CallMatcher> callMatchers = new ArrayList<CallMatcher>();
  
  public DispatcherMatcher(Object subject) {
    
    Dispatcher annotation = subject.getClass().getAnnotation(Dispatcher.class);
    if(annotation != null) {
      if(!annotation.pattern().equals(Dispatcher.NULL)) {
        pattern = annotation.pattern();
        if(annotation.method() != HTTPMethod.UNDEFINED) {
          httpMethod = annotation.method();
        }
      }
    }
    

    for(Method method: subject.getClass().getMethods()) {
      Action action = method.getAnnotation(Action.class);
      if(action != null) {
        CallMatcher cm = new CallMatcher(pattern, httpMethod, subject, method, action);
        callMatchers.add(cm);
      }
    }
    
  }
  
  public List<Call> getCall(String path, HTTPMethod method) {
    List<Call> calls = null;
    for(CallMatcher cm: callMatchers) {
      Call call = cm.getCall(path, method);
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
