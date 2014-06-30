package ch.kerbtier.webb.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface Column {

  String name() default NULL;

  public static final String NULL = "NULL_oZShvXLv0RMTBhe61cN7";

}