package ch.kerbtier.webb.dispatcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Dispatcher {
  String pattern() default NULL;
  public static final String NULL = "NULL_oZShvXLv0RMTBhe61cN7";
}
