package com.useraccount.application;

import com.useraccount.UserAccountApplication;
import com.useraccount.di.framework.annotations.CustomApplication;
import com.useraccount.di.injector.CustomInjector;

public class CustomDemoApplication {

	private final CustomInjector injector;

	public CustomDemoApplication() {
		super();
		this.injector = new CustomInjector();
	}

	public void run(Class<?> mainClassz) {
		boolean hasCustomApplicationAnnotation = mainClassz.isAnnotationPresent(CustomApplication.class);

		if (hasCustomApplicationAnnotation) {
			System.out.println("Starting CustomDemoApplication...");
			this.startApplication(UserAccountApplication.class);
			this.injector.getService(ClientApplicacation.class).displayUserAccount();
			System.out.println("\nStopping CustomDemoApplication...");
		} else {
			System.out.println("\nRunning as regular java Application...");
		}
	}

	/**
	 * Start application
	 * 
	 * @param mainClass
	 */
	public void startApplication(Class<?> mainClass) {
		try {
			synchronized (CustomDemoApplication.class) {
				this.injector.initFramework(mainClass);
				System.out.println("\nCustomDemoApplication Started....");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void run(Class<?> mainClassz, String[] args) {
		new CustomDemoApplication().run(mainClassz);
	}
}
