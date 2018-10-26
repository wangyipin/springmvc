package com.framework.core.dao.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.framework.core.dao.IBaseDao;

@Component
public class BaseDao implements IBaseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	@Override
	public List query(String sql, Class clazz) {
		return jdbcTemplate.query(sql, resultBeanMapper(clazz));
	}

	@Override
	public int execSql(String sql) {
		return jdbcTemplate.update(sql);
	}
	
	protected BeanPropertyRowMapper resultBeanMapper(Class clazz)
	{
		return new BeanPropertyRowMapper(clazz);
	}

}
