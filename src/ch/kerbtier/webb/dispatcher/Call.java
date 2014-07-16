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

    if (pts.length != matcher.groupCount()) {
      throw new RuntimeException("wrong number of parameters for " + method);
    }

    Object[] parameters = new Object[pts.length];

    for (int cnt = 0; cnt < matcher.groupCount(); cnt++) {
      String part = matcher.group(cnt + 1);

      Class<?> parameterType = pts[cnt];

      if (Integer.TYPE.isAssignableFrom(parameterType)) {
        parameters[cnt] = Integer.parseInt(part);
      } else if(Integer.class.isAssignableFrom(parameterType)) {
        if("null".equals(part)) {
          parameters[cnt] = (Integer)null;
        } else {
          parameters[cnt] = Integer.parseInt(part);
        }
      } else if (String.class.isAssignableFrom(parameterType)) {
        parameters[cnt] = part;
      } else if (parameterType.isEnum()) {
        parameters[cnt] = Enum.valueOf(((Class<? extends Enum>) parameterType),
            part.toUpperCase());
      } else if (Boolean.TYPE.isAssignableFrom(parameterType)
          || Boolean.class.isAssignableFrom(parameterType)) {
        parameters[cnt] = Boolean.parseBoolean(part);

      } else {
        throw new RuntimeException("invalid parameter type: " + parameters[cnt]);
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
