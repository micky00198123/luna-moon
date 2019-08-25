package com.qklt.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Administrator
 *
 * @param <T> DAO处理的实体类型
 */
public interface DAO<T> {

	/**
	 * 是否找到对应记录
	 * @param sql
	 * @param args
	 * @return
	 */
	public Boolean isFound(Connection conn, String sql, Object... args) throws SQLException;

	/**
	 * 封装 INSERT DELETE UPDATE 操作
	 * @param sql 语句
	 * @param args 填充占位符
	 */
	public boolean save(Connection conn, String sql, Object... args) throws SQLException;

	/**
	 * 返回T对应的实例对象
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(Connection conn, String sql, Object... args) throws SQLException;


	
}
