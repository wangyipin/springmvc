package com.apps.login.dao;

import com.apps.login.domain.ResponseBean;
import com.apps.login.domain.User;

public interface IUserDao {

	public ResponseBean register(User user);
	
}
