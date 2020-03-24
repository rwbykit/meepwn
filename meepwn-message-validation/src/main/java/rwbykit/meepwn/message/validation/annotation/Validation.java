package rwbykit.meepwn.message.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Cytus_
 */
@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Validations.class)
public @interface Validation {

    boolean required() default false;

    String dictId() default "";

    int scale() default 0;

    int minlength() default Integer.MIN_VALUE;

    int maxlength() default Integer.MAX_VALUE;

    String relyname() default "";

    String relyvalue() default "";

    String[] group() default "";

}
