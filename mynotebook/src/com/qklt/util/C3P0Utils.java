package com.qklt.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 使用c3p0连接池
 */
public class C3P0Utils {

	private static DataSource dataSource = null;

	static{
		//数据源只能创建一次（虽然创建多次理论上没问题，但浪费资源）
		dataSource = new ComboPooledDataSource("myNoteBook");
	}
	
	/**
	 * 返回数据源的一个 Connection 对象
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() {
		// 类似于以前Connection conn = new Connection...
		// 这里获取connection连接直接从数据源获取
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 释放 Connection 连接
	 * @param connection
	 */
	public static void releaseConnection(Connection connection){
		if(connection != null)
			try {
				//这里的close不像以前那样真的就close了
				//而是把连接还给连接池
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 事务回滚
	 * @param connection
	 */
	public static void rollbackTransaction(Connection connection){
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
