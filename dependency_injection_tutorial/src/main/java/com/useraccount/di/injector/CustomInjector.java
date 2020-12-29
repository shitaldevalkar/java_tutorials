package com.useraccount.di.injector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.reflections.Reflections;

import com.useraccount.di.framework.annotations.CustomComponent;
import com.useraccount.di.framework.utils.ClassLoaderUtil;
import com.useraccount.di.framework.utils.InjectionUtil;

/**
 * Injector, to create objects for all @CustomService classes. autowire/inject
 * all dependencies
 */
public class CustomInjector {
	private Map<Class<?>, Class<?>> diMap;
	private Map<Class<?>, Object> applicationScope;

	public CustomInjector() {
		super();
		diMap = new HashMap<>();
		applicationScope = new HashMap<>();
	}

	public <T> T getService(Class<T> classz) {
		try {
			return this.getBeanInstance(classz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * initialize the injector framework
	 */
	public void initFramework(Class<?> mainClass)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Class<?>[] classes = ClassLoaderUtil.getClasses(mainClass.getPackage().getName());
		Reflections reflections = new Reflections(mainClass.getPackage().getName());
		Set<Class<?>> types = reflections.getTypesAnnotatedWith(CustomComponent.class);
		for (Class<?> implementationClass : types) {
			Class<?>[] interfaces = implementationClass.getInterfaces();
			if (interfaces.length == 0) {
				diMap.put(implementationClass, implementationClass);
			} else {
				for (Class<?> iface : interfaces) {
					diMap.put(implementationClass, iface);
				}
			}
		}

		for (Class<?> classz : classes) {
			if (classz.isAnnotationPresent(CustomComponent.class)) {
				Object classInstance = classz.newInstance();
				applicationScope.put(classz, classInstance);
				InjectionUtil.autowire(this, classz, classInstance);
			}
		}
	}

	/**
	 * Create and Get the Object instance of the implementation class for input
	 * interface service
	 */
	@SuppressWarnings("unchecked")
	private <T> T getBeanInstance(Class<T> interfaceClass) throws InstantiationException, IllegalAccessException {
		return (T) getBeanInstance(interfaceClass, null, null);
	}

	/**
	 * Overload getBeanInstance to handle qualifier and autowire by type
	 */
	public <T> Object getBeanInstance(Class<T> interfaceClass, String fieldName, String qualifier)
			throws InstantiationException, IllegalAccessException {
		Class<?> implementationClass = getImplimentationClass(interfaceClass, fieldName, qualifier);

		if (applicationScope.containsKey(implementationClass)) {
			return applicationScope.get(implementationClass);
		}

		synchronized (applicationScope) {
			Object service = implementationClass.newInstance();
			applicationScope.put(implementationClass, service);
			return service;
		}
	}

	/**
	 * Get the name of the implimentation class for input interface service
	 */
	private Class<?> getImplimentationClass(Class<?> interfaceClass, final String fieldName, final String qualifier) {
		Set<Entry<Class<?>, Class<?>>> implementationClasses = diMap.entrySet().stream()
				.filter(entry -> entry.getValue() == interfaceClass).collect(Collectors.toSet());
		String errorMessage = "";
		if (implementationClasses == null || implementationClasses.isEmpty()) {
			errorMessage = "no implementation found for interface " + interfaceClass.getName();
		} else if (implementationClasses.size() == 1) {
			Optional<Entry<Class<?>, Class<?>>> optional = implementationClasses.stream().findFirst();
			if (optional.isPresent()) {
				return optional.get().getKey();
			}
		} else if (implementationClasses.size() > 1) {
			final String findBy = (qualifier == null || qualifier.trim().length() == 0) ? fieldName : qualifier;
			Optional<Entry<Class<?>, Class<?>>> optional = implementationClasses.stream()
					.filter(entry -> entry.getKey().getSimpleName().equalsIgnoreCase(findBy)).findAny();
			if (optional.isPresent()) {
				return optional.get().getKey();
			} else {
				errorMessage = "There are " + implementationClasses.size() + " of interface " + interfaceClass.getName()
						+ " Expected single implementation or make use of @CustomQualifier to resolve conflict";
			}
		}
		throw new RuntimeErrorException(new Error(errorMessage));
	}
}