package ch.kerbtier.webb.dispatcher;

import java.lang.reflect.Method;
import java.util.regex.Matcher;

public class Call {

  private Object subject;
  private Method method;
  private Matcher matcher;
  
  public Call(Object subject, Method method, Matcher matcher) {
    this.subject = subject;
    this.method = method;
    this.matcher = matcher;
  }

  public void execute() {
    Class<?>[] pts = method.getParameterTypes();
    Object[] parameters = new Object[pts.length];
    
    for(int cnt = 0; cnt < matcher.groupCount(); cnt++) {
      String part = matcher.group(cnt + 1);
      
      Class<?> parameterType = pts[cnt];
      
      if(Integer.TYPE.isAssignableFrom(parameterType) || Integer.class.isAssignableFrom(parameterType)) {
        parameters[cnt] = Integer.parseInt(part);
      } else if(String.class.isAssignableFrom(parameterType)) {
        parameters[cnt] = part;
      }
    }
    
    try {
      method.invoke(subject, parameters);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    
  }
  
  public String toString() {
    return "<" + method.toString() + ">";
  }

}
