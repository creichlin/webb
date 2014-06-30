package ch.kerbtier.webb.dispatcher;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallMatcher {

  private Object subject;
  private Method method;
  private Pattern pattern;
//  private HTTPMethod httpMethod;
  
  public CallMatcher(String parentPattern, Object subject, Method method, Action action) {
    this.subject = subject;
    this.method = method;
//    this.httpMethod = action.method();
    
    String fixedPattern = "^";
    if(parentPattern != null) {
      fixedPattern += parentPattern;
    }
    
    
    if(action.pattern().equals(Action.NULL)) {
      fixedPattern += method.getName();
    } else {
      fixedPattern += action.pattern();
    }
    
    fixedPattern += "$";
    
    pattern = Pattern.compile(fixedPattern);
  }
  
  public Call getCall(String path) {
    Matcher matcher = pattern.matcher(path);
    
    if(matcher.find()) {
      return new Call(subject, method, matcher);
    }
    return null;
  }

}
