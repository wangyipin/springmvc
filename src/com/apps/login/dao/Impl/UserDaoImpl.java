package com.apps.login.dao.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.apps.login.dao.IUserDao;
import com.apps.login.domain.ResponseBean;
import com.apps.login.domain.User;
import com.framework.core.utils.Const;
@Repository
public class UserDaoImpl implements IUserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public ResponseBean register(User user) {
		ResponseBean responseBean = new ResponseBean();
		
		String sql = "select * from t_user where username = '"+user.getUsername()+"'";
		
		System.out.println("----------------------------------"+sql);
		
		List list = jdbcTemplate.queryForList(sql);
		
		if(list.size() > 0) {
			responseBean.setErrMsg("�û����Ѿ�����");
			responseBean.setRetCode(Const.REGISTER_ERROR);
			return responseBean;
		}
		sql = "insert into t_user(username,pwd) values('"+user.getUsername()+"','"+user.getPwd()+"')";
		int i = jdbcTemplate.update(sql);
		if(i != -1) {
			responseBean.setRetCode(Const.REGISTER_SUCCESS);
			responseBean.setResponseData("ע��ɹ�");
		}
		return responseBean;
	}

}
