package com.di;

public class DIApplication {
	  private final CustomInjector injector;
	  public DIApplication() {
	  super();
	  this.injector = new CustomInjector();
	}
	 public void run(Class<?> mainClassz) {
	    boolean hasCustomApplicationAnnotation =
				mainClassz.isAnnotationPresent(CustomApplication.class);
	                                           
	   if(hasCustomApplicationAnnotation) {
	     System.out.println("Starting CustomDemoApplication...");
	     this.startApplication(MainApplicationClass.class);
	     this.injector    
	      .getService(ClientApplication.class).displayUserAccount();
	     System.out.println("\nStopping CustomDemoApplication...");
	   }else{
	     System.out.println("\nRunning as regular java Application...");
	   }
	 }
	 /**
	  * Start application
	  * @param mainClass
	  */
	  public void startApplication(Class<?> mainClass) {
	    try {
	      synchronized (DIApplication.class) {
	       this.injector.initFramework(mainClass);
	        System.out.println("\nCustomDemoApplication Started....");
	      }
	    }catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
	  public static void run(Class<?> mainClassz, String[] args){
	     new DIApplication().run(mainClassz);
	  }
	}