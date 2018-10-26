package com.apps.login.dao.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.apps.login.dao.ILoginDao;
import com.apps.login.domain.User;
import com.framework.core.dao.IBaseDao;
import com.framework.core.dao.Impl.BaseDao;

@Repository
public class LoginDaoImpl implements ILoginDao{

	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public boolean login(User user) {
		
		String sql = "select username,pwd password from t_user where username = '"+user.getUsername()+"' and pwd = '"+user.getPwd()+"'";
		//方法一
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		//方法二，利用封装dao
		List list2 = baseDao.query(sql, user.getClass());
		System.out.println(JSON.toJSONString(list2));
		return list.size() != 0;
	}

}
