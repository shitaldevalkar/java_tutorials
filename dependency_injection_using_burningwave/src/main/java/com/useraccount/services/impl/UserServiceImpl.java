package com.useraccount.services.impl;

import com.useraccount.di.framework.annotations.CustomComponent;
import com.useraccount.services.UserService;

@CustomComponent
public class UserServiceImpl implements UserService {

	@Override
	public String getUserName() {
		return "shital.devalkar";
	}
}
