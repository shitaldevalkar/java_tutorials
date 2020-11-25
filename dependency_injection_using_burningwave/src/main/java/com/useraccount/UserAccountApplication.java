package com.useraccount;

import com.useraccount.di.framework.CustomInjector;

public class UserAccountApplication {

	public static void main(String[] args) {
		CustomInjector.startApplication(UserAccountApplication.class);

		CustomInjector.getService(UserAccountClientComponent.class).displayUserAccount();
	}
}
