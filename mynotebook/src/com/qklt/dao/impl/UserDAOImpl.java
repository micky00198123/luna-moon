package com.qklt.dao.impl;

import com.qklt.domain.LoginData;
import com.qklt.domain.User;
import com.qklt.util.C3P0Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAOImpl extends DAOImpl<User> {

	/*
		ĳЩ�����ṩ����,�Թ�����ع�
		��ĳ�������ⲿ����connection,��÷����������connection�Ĺرղ���
		�÷�����connection�����Ĺرղ��������η������н��
		�Ҹ÷������ᴦ���쳣,�쳣һ���������׳�
	 */

	Connection connection = null;

	/**
	 * ����û����������Ƿ�ƥ��
	 * @param ld
	 * @return
	 */
	public boolean checkAccount(LoginData ld) {
		connection = C3P0Utils.getConnection();
		String sql = "SELECT count(*) FROM users_account WHERE userName = ? " +
				"AND password = ?";
		try {
			return super.isFound(connection, sql, ld.getUserName(), ld.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			C3P0Utils.releaseConnection(connection);
		}
	}

	/**
	 * ʹ���û�����ȡ������Ϣ
	 * @param userName
	 * @return
	 */
	public User getUserByName(String userName) {
		connection = C3P0Utils.getConnection();
		String sql = "SELECT * FROM users_information WHERE userName = ?";
		try {
			return super.get(connection, sql, userName);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			C3P0Utils.releaseConnection(connection);
		}
	}

	/**
	 * �������
	 * @param userName
	 * @return
	 */
	public boolean checkDuplicateNames(String userName){
		connection = C3P0Utils.getConnection();
		String sql = "SELECT count(*) FROM users_account WHERE userName = ?";
		try {
			return super.isFound(connection, sql, userName);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			C3P0Utils.releaseConnection(connection);
		}
	}

    /**
     * ���˻��������ݿ����Ӽ�¼
     * @param ld
     * @return
     */
	public boolean updateUserToAccount(Connection conn, LoginData ld) throws SQLException {
        String sql = "INSERT INTO users_account(userName, password) "
                + "VALUES (?,?)";
        return super.save(conn, sql, ld.getUserName(), ld.getPassword());
	}

    public boolean updateUserToAccount(LoginData ld){
		connection = C3P0Utils.getConnection();
		try {
			return this.updateUserToAccount(connection, ld);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			C3P0Utils.releaseConnection(connection);
		}
	}

    /**
     * ���û���Ϣ���ݿ����Ӽ�¼
     * @param user ֻ����userName,�����ֶ�ʹ��Ĭ������
     * @return
     */
	public boolean updateUserToInformation(Connection conn, User user) throws SQLException {
        String sql = "INSERT INTO users_information(userName) "
                + "VALUES (?)";
        return super.save(conn, sql, user.getUserName());
	}

    public boolean updateUserToInformation(User user){
		connection = C3P0Utils.getConnection();
		try {
			return this.updateUserToInformation(connection, user);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			C3P0Utils.releaseConnection(connection);
		}
	}

}