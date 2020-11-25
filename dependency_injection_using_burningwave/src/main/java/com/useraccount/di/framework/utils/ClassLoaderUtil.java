package com.useraccount.di.framework.utils;

import java.io.IOException;
import java.util.Collection;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.classes.CacheableSearchConfig;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.ClassHunter.SearchResult;
import org.burningwave.core.classes.SearchConfig;

public class ClassLoaderUtil {

	private ClassLoaderUtil() {
		super();
	}

	/**
	 * Get all the classes for the input package
	 */
	public static Class<?>[] getClasses(String packageName, boolean recursive) throws ClassNotFoundException, IOException {
		ComponentContainer componentConatiner = ComponentContainer.getInstance();
		ClassHunter classHunter = componentConatiner.getClassHunter();
		String packageRelPath = packageName.replace(".", "/");
		CacheableSearchConfig config = SearchConfig.forResources(
			Thread.currentThread().getContextClassLoader(),
			packageRelPath
		);
		if (!recursive) {
			config.notRecursiveOnPath(
				packageRelPath, false
			);
		}
		
		try (SearchResult result = classHunter.loadInCache(config).find()) {
			Collection<Class<?>> classes = result.getClasses();
			return classes.toArray(new Class[classes.size()]);
		}	
	}

}
