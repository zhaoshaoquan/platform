package com.stone.commons;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

public class BeanUtils extends org.springframework.beans.BeanUtils{

	public static void copyNotNullProperties(Object source, Object target){
		copyNotNullProperties(source, target, null, null);
	}

	public static Object getBeanValue(Object item, String propName) throws Exception{
		Class<?> clazz = item.getClass();
		Field field = clazz.getDeclaredField(propName);
		field.setAccessible(true);
		return field.get(item);
	}

	public static void copyNotNullProperties(Object source, Object target, String...ignoreProperties) throws BeansException{
		copyNotNullProperties(source, target, null, ignoreProperties);
	}

	public static void copyNotNullProperties(Object source, Object target, Class<?> editable, String[] ignoreProperties){
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if(editable != null){
			if(!editable.isInstance(target)){
				throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for(PropertyDescriptor targetPd : targetPds){
			if(targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))){
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if(sourcePd != null && sourcePd.getReadMethod() != null){
					try{
						Method readMethod = sourcePd.getReadMethod();

						if(!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())){
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						if(value != null){
							Method writeMethod = targetPd.getWriteMethod();
							if(!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())){
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					}catch(Throwable ex){
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}

}
