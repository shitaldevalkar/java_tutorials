package com.useraccount;

import com.useraccount.application.CustomDemoApplication;
import com.useraccount.di.framework.annotations.CustomApplication;

@CustomApplication
public class UserAccountApplication {

	public static void main(String[] args) {
		CustomDemoApplication.run(UserAccountApplication.class, args);
	}
}
