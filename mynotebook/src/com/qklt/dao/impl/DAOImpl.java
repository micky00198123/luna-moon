package com.qklt.dao.impl;

import com.qklt.dao.DAO;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @param <T>
 * @author Administrator
 */
public class DAOImpl<T> implements DAO<T> {

    private QueryRunner queryRunner = new QueryRunner();

    //Class<T>����ֱ��Class:����Ҫ��Class�Ĳ������ͺ�DAOһ��
	private Class<T> clazz;

    //���췽��
    public DAOImpl() {
        //Type�� Java���������͵Ĺ����߼��ӿ�,������Object�����еĵ�λ
        Type superClass = this.getClass().getGenericSuperclass();

        //�����������
        //���newһ��DAO������:CustomerDAO customerDAO = new CustomerDAOJdbcImpl();
        //class CustomerDAOJdbcImpl extends DAO<Customer> implements CustomerDAO

        //com.qklt.mvcapp.dao.impl.CustomerDAOJdbcImpl@6eb02efa
        //System.out.println(this);��ǰ����CustomerDAOJdbcImpl

        //class com.qklt.mvcapp.dao.impl.CustomerDAOJdbcImpl
        //System.out.println(this.getClass());��õ�ǰ���ȫ����

        //com.qklt.mvcapp.dao.DAO<com.qklt.mvcapp.domain.Customer> �������superClass
        //System.out.println(this.getClass().getGenericSuperclass());��ô��з��͵ĸ���

        //���superClass��������Ǵ���������(�м�����),��DAO<Customer>
        if (superClass instanceof ParameterizedType) {

            //ParameterizedType��Type�ӽӿ�,Class��Typeʵ����
            ParameterizedType parameterizedType = (ParameterizedType) superClass;

            //getActualTypeArguments��ȡʵ�����Ͳ���,�������T,ʵ�ʵ���Customer
            Type[] typeArgs = parameterizedType.getActualTypeArguments();

            //typeArgs��һ��������Ϊ�����в�ֹһ�����Ͳ���,��DAO<A,B,C,D...>,ֻ���������һ������T����
            if (typeArgs != null && typeArgs.length > 0) {
                if (typeArgs[0] instanceof Class) {
                    // ���ջ�ȡ����clazz��domain�е�ģ��,Ҳ�ɿ�������ķ��Ͳ���T
                    clazz = (Class<T>) typeArgs[0];
                }
            }
        }

    }

    /*
        Ϊ�˿�������ع�,����connection���ڴ˹ر�
        �쳣һ���������׳�
     */

    @Override
    public Boolean isFound(Connection conn, String sql, Object... args) throws SQLException {
        //ʹ��queryRunner.query,ResultsetHandlerʹ��ScalarHandler,���ص���Object����
        Object obj = queryRunner.query(conn, sql, new ScalarHandler(), args);
        if (obj != null) {

            //obj��ת��String��ת��Integer
            int count = Integer.parseInt(String.valueOf(obj));
            // �����һ������,�����ҵ���
            if (count == 1)
                return true;
        }
        return false;
    }


    @Override
    public boolean save(Connection conn, String sql, Object... args) throws SQLException {
        queryRunner.update(conn, sql, args);
        return true;
    }


    @Override
    public T get(Connection conn, String sql, Object... args) throws SQLException {
        // query����BeanHandler�᷵��clazz��Ӧ��ʵ������
        return queryRunner.query(conn, sql, new BeanHandler<>(clazz), args);
    }


}
