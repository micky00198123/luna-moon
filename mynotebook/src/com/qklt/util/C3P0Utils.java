package com.qklt.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * ʹ��c3p0���ӳ�
 */
public class C3P0Utils {

	private static DataSource dataSource = null;

	static{
		//����Դֻ�ܴ���һ�Σ���Ȼ�������������û���⣬���˷���Դ��
		dataSource = new ComboPooledDataSource("myNoteBook");
	}
	
	/**
	 * ��������Դ��һ�� Connection ����
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() {
		// ��������ǰConnection conn = new Connection...
		// �����ȡconnection����ֱ�Ӵ�����Դ��ȡ
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * �ͷ� Connection ����
	 * @param connection
	 */
	public static void releaseConnection(Connection connection){
		if(connection != null)
			try {
				//�����close������ǰ������ľ�close��
				//���ǰ����ӻ������ӳ�
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	/**
	 * ����ع�
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
