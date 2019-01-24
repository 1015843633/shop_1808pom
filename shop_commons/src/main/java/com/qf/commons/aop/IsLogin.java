package com.qf.commons.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author CZF
 * @Date 2019/1/23
 * @Version 1.0
 *@Retention(RetentionPolicy.RUNTIME) 表示当前注解有效范围在运行时，如果你的注解需要被反射操作
 * @Target 表示当前注解的可标记位置
 * ElementType.ANNOTATION_TYPE表示当前注解可以标记其他的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IsLogin {
    boolean tologin() default false;
}
