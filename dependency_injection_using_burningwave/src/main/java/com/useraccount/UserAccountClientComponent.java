package com.useraccount;

import com.useraccount.di.framework.annotations.*;
import com.useraccount.services.*;

/**
 * Client class, havin userService and accountService expected to initialized by
 * CustomInjector.java
 */
@CustomComponent
public class UserAccountClientComponent {

	@CustomAutowired
	private UserService userService;

	@CustomAutowired
	@CustomQualifier(value = "accountServiceImpl")
	private AccountService accountService;

	public void displayUserAccount() {

		String username = userService.getUserName();

		Long accountNumber = accountService.getAccountNumber(username);

		System.out.println("User Name: " + username + "Account Number: " + accountNumber);
	}
}
