package ch.kerbtier.webb.dispatcher;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import ch.kerbtier.webb.util.HTTPMethod;

public class CallMatcher {

  private Object subject;
  private Method method;
  private Pattern pattern;
  private HTTPMethod httpMethod;

  // private HTTPMethod httpMethod;

  public CallMatcher(String parentPattern, HTTPMethod httpMethod, Object subject, Method method, Action action) {
    this.subject = subject;
    this.method = method;
    // this.httpMethod = action.method();

    String fixedPattern = "^";
    if (parentPattern != null) {
      fixedPattern += parentPattern;
    }

    if (action.pattern().equals(Action.NULL)) {
      fixedPattern += method.getName();
    } else {
      fixedPattern += action.pattern();
    }

    fixedPattern += "$";

    if (action.method() != HTTPMethod.UNDEFINED) {
      this.httpMethod = action.method();
    } else {
      this.httpMethod = httpMethod;
    }

    try {
      pattern = Pattern.compile(fixedPattern);
    } catch (PatternSyntaxException e) {
      throw new RuntimeException("method " + method + " has invalid pattern.", e);
    }
  }

  public Call getCall(String path, HTTPMethod httpMethod) {
    if (this.httpMethod == httpMethod) {
      Matcher matcher = pattern.matcher(path);

      if (matcher.find()) {
        return new Call(subject, method, matcher);
      }
    }
    return null;
  }

}
