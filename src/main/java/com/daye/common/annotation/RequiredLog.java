package com.daye.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 自定义注解(修饰业务层方法,定义具体操作名称)
 * @author whd
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredLog {

	int value() default 1;

	 String operation() default "unkown";
	 //.....
}











