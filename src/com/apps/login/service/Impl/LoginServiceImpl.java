package com.apps.login.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apps.login.dao.Impl.LoginDaoImpl;
import com.apps.login.domain.User;
import com.apps.login.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private LoginDaoImpl loginDaoImpl;
	
	
	@Override
	public boolean login(User user) {
		
		return loginDaoImpl.login(user);
	}

}
