package com.stone.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标识Freemarker标签注解
 * @author 赵少泉
 * @date 2015年10月28日 下午2:58:26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FreemarkerTag{
	/**
	 * 标签名称，该名称就是在模板中使用的名称 
	 */
	String name();
}
