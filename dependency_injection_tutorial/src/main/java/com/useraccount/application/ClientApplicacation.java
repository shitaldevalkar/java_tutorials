package com.useraccount.application;

import com.useraccount.di.framework.annotations.*;
import com.useraccount.services.AccountService;
import com.useraccount.services.UserService;

/**
 * Client class, havin userService and accountService expected to initialized by
 * CustomInjector.java
 */
@CustomComponent
public class ClientApplicacation {

	@CustomAutowired
	private UserService userService;

	@CustomAutowired
	@CustomQualifier(value = "accountServiceImpl")
	private AccountService accountService;

	public void displayUserAccount() {

		String username = userService.getUserName();

		Long accountNumber = accountService.getAccountNumber(username);

		System.out.println("\nUser Name: " + username + " Account Number: " + accountNumber);
	}
}
