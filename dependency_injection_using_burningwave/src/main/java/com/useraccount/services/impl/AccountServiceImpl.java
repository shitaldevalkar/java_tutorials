package com.useraccount.services.impl;

import com.useraccount.di.framework.annotations.CustomComponent;
import com.useraccount.services.AccountService;

@CustomComponent
public class AccountServiceImpl implements AccountService {

	@Override
	public Long getAccountNumber(String userName) {
		return 12345689L;
	}
}
