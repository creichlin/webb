package ch.kerbtier.webb.dispatcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.kerbtier.webb.util.HTTPMethod;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {
  String pattern() default NULL;
  HTTPMethod method() default HTTPMethod.UNDEFINED;
  public static final String NULL = "NULL_oZShvXLv0RMTBhe61cN7";
}
