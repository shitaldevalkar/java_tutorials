package com.di;

/**
 * Client class, havin userService and accountService expected to initialized by
 * CustomInjector.java
 */
@CustomComponent
public class ClientApplication {
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
