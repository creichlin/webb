package ch.kerbtier.webb.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.kerbtier.esdi.Inject;

@Inject
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface InjectRequest {

}
