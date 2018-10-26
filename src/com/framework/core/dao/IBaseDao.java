package com.framework.core.dao;

import java.util.List;

public interface IBaseDao {

	public List query(String sql, Class clazz);
	
	public int execSql(String sql);
	
}
