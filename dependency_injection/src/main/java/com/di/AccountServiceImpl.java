package com.di;

@CustomComponent
public class AccountServiceImpl implements AccountService {
	@Override
	public Long getAccountNumber(String userName) {
		return 12345689L;
	}
}