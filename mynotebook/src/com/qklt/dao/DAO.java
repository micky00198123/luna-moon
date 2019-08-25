package com.qklt.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Administrator
 *
 * @param <T> DAO�����ʵ������
 */
public interface DAO<T> {

	/**
	 * �Ƿ��ҵ���Ӧ��¼
	 * @param sql
	 * @param args
	 * @return
	 */
	public Boolean isFound(Connection conn, String sql, Object... args) throws SQLException;

	/**
	 * ��װ INSERT DELETE UPDATE ����
	 * @param sql ���
	 * @param args ���ռλ��
	 */
	public boolean save(Connection conn, String sql, Object... args) throws SQLException;

	/**
	 * ����T��Ӧ��ʵ������
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(Connection conn, String sql, Object... args) throws SQLException;


	
}
