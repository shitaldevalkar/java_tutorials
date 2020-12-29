package com.useraccount.di.framework.utils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import com.useraccount.di.framework.annotations.CustomAutowired;
import com.useraccount.di.framework.annotations.CustomQualifier;
import com.useraccount.di.injector.CustomInjector;

public class InjectionUtil {

	private InjectionUtil() {
		super();
	}

	/**
	 * Perform injection recursively, for each service inside the Client class
	 */
	public static void autowire(CustomInjector injector, Class<?> classz, Object classInstance)
			throws InstantiationException, IllegalAccessException {
		Set<Field> fields = findFields(classz);
		for (Field field : fields) {
			String qualifier = field.isAnnotationPresent(CustomQualifier.class)
					? field.getAnnotation(CustomQualifier.class).value()
					: null;
			Object fieldInstance = injector.getBeanInstance(field.getType(), field.getName(), qualifier);
			field.set(classInstance, fieldInstance);
			autowire(injector, fieldInstance.getClass(), fieldInstance);
		}
	}

	/**
	 * Get all the fields having CustomAutowired annotation used while declaration
	 */
	private static Set<Field> findFields(Class<?> classz) {
		Set<Field> set = new HashSet<>();
		while (classz != null) {
			for (Field field : classz.getDeclaredFields()) {
				if (field.isAnnotationPresent(CustomAutowired.class)) {
					field.setAccessible(true);
					set.add(field);
				}
			}
			classz = classz.getSuperclass();
		}
		return set;

	}
}
