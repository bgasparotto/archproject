package com.bgasparotto.archproject.infrastructure.interceptor;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Types or methods annotated with {@code ExceptionLogging} will log any thrown
 * exceptions in the default {@code Logger} object, so logging strategies don't
 * need to be mixed with logic and exception handling.
 * 
 * @author Bruno Gasparotto
 *
 */
@Inherited
@InterceptorBinding
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ExceptionLogging {

}