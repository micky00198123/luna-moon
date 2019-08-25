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

    //Class<T>而非直接Class:限制要求Class的参数类型和DAO一样
	private Class<T> clazz;

    //构造方法
    public DAOImpl() {
        //Type是 Java中所有类型的公共高级接口,类似于Object在类中的地位
        Type superClass = this.getClass().getGenericSuperclass();

        //下面操作举例
        //如果new一个DAO的子类:CustomerDAO customerDAO = new CustomerDAOJdbcImpl();
        //class CustomerDAOJdbcImpl extends DAO<Customer> implements CustomerDAO

        //com.qklt.mvcapp.dao.impl.CustomerDAOJdbcImpl@6eb02efa
        //System.out.println(this);当前类是CustomerDAOJdbcImpl

        //class com.qklt.mvcapp.dao.impl.CustomerDAOJdbcImpl
        //System.out.println(this.getClass());获得当前类的全类名

        //com.qklt.mvcapp.dao.DAO<com.qklt.mvcapp.domain.Customer> 这个就是superClass
        //System.out.println(this.getClass().getGenericSuperclass());获得带有泛型的父类

        //如果superClass里面的类是带参数的类(有尖括号),如DAO<Customer>
        if (superClass instanceof ParameterizedType) {

            //ParameterizedType是Type子接口,Class是Type实现类
            ParameterizedType parameterizedType = (ParameterizedType) superClass;

            //getActualTypeArguments获取实际类型参数,抽象的是T,实际的是Customer
            Type[] typeArgs = parameterizedType.getActualTypeArguments();

            //typeArgs是一个数组因为可能有不止一个泛型参数,如DAO<A,B,C,D...>,只是这里仅有一个参数T罢了
            if (typeArgs != null && typeArgs.length > 0) {
                if (typeArgs[0] instanceof Class) {
                    // 最终获取到的clazz是domain中的模型,也可看作这里的泛型参数T
                    clazz = (Class<T>) typeArgs[0];
                }
            }
        }

    }

    /*
        为了可用事务回滚,所有connection不在此关闭
        异常一律向上游抛出
     */

    @Override
    public Boolean isFound(Connection conn, String sql, Object... args) throws SQLException {
        //使用queryRunner.query,ResultsetHandler使用ScalarHandler,返回单个Object对象
        Object obj = queryRunner.query(conn, sql, new ScalarHandler(), args);
        if (obj != null) {

            //obj先转成String再转成Integer
            int count = Integer.parseInt(String.valueOf(obj));
            // 如果有一行数据,就是找到了
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
        // query传参BeanHandler会返回clazz对应的实例对象
        return queryRunner.query(conn, sql, new BeanHandler<>(clazz), args);
    }


}
