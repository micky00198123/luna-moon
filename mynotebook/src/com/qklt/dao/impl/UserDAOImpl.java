package com.qklt.dao.impl;

import com.qklt.domain.LoginData;
import com.qklt.domain.User;
import com.qklt.util.C3P0Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAOImpl extends DAOImpl<User> {

	/*
		某些方法提供重载,以供事务回滚
		若某方法需外部传入connection,则该方法不会进行connection的关闭操作
		该方法的connection参数的关闭操作由上游方法自行解决
		且该方法不会处理异常,异常一律向上游抛出
	 */

	Connection connection = null;

	/**
	 * 检查用户名和密码是否匹配
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
	 * 使用用户名获取个人信息
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
	 * 检查重名
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
     * 向账户管理数据库添加记录
     * @param ld
     * @return
     */
	public boolean insertUserToAccount(Connection conn, LoginData ld) throws SQLException {
        String sql = "INSERT INTO users_account(userName, password) "
                + "VALUES (?,?)";
        return super.save(conn, sql, ld.getUserName(), ld.getPassword());
	}

    public boolean insertUserToAccount(LoginData ld){
		connection = C3P0Utils.getConnection();
		try {
			return this.insertUserToAccount(connection, ld);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			C3P0Utils.releaseConnection(connection);
		}
	}

    /**
     * 向用户信息数据库添加记录
     * @param user 只插入userName,其他字段使用默认设置
     * @return
     */
	public boolean insertUserToInformation(Connection conn, User user) throws SQLException {
        String sql = "INSERT INTO users_information(userName) "
                + "VALUES (?)";
        return super.save(conn, sql, user.getUserName());
	}

    public boolean insertUserToInformation(User user){
		connection = C3P0Utils.getConnection();
		try {
			return this.insertUserToInformation(connection, user);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			C3P0Utils.releaseConnection(connection);
		}
	}

	/**
	 * 向用户信息数据库修改记录
	 * @param user
	 * @return
	 */
	public boolean updateUserInformation(User user) {
		connection = C3P0Utils.getConnection();
		String sql = "UPDATE users_information " +
				"SET sex = ?, age = ?, portrait = ?" +
				"WHERE userName = ?";
		try {
			return save(connection, sql, user.getSex(),
					user.getAge(), user.getPortrait(), user.getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			C3P0Utils.releaseConnection(connection);
		}
	}

}
