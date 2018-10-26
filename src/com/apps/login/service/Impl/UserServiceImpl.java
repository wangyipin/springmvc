package com.apps.login.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.apps.login.dao.Impl.UserDaoImpl;
import com.apps.login.domain.ResponseBean;
import com.apps.login.domain.User;
import com.apps.login.service.IUserService;
import com.framework.core.utils.Const;

@Service
public class UserServiceImpl implements IUserService {

   @Autowired
   private UserDaoImpl userDaoImpl;
	
	@Override
	public ResponseBean register(User user) {
		
		ResponseBean responseBean = new ResponseBean();
		if(user.getUsername().equals("") || user.getPwd().equals(null)) {
			responseBean.setErrMsg("�û�������Ϊ��");
			responseBean.setRetCode("01");
		}
		if(user.getPwd().equals("") || user.getPwd().equals(null)) {
			responseBean.setErrMsg("ע�����벻��Ϊ��");
			responseBean.setRetCode("01");
		}
		
		return userDaoImpl.register(user);
	}

}
