package com.qklt.service.Impl;

import com.qklt.dao.impl.UserDAOImpl;
import com.qklt.domain.LoginData;
import com.qklt.domain.User;
import com.qklt.service.RegisterService;
import com.qklt.util.C3P0Utils;
import com.qklt.util.ServiceUtils;

import java.sql.Connection;

public class RegisterServiceImpl implements RegisterService {

    private UserDAOImpl ud = new UserDAOImpl();

    @Override
    public boolean registerAccount(LoginData ld) {

        Connection conn = null;
        User user = new User();
        user.setUserName(ld.getUserName());
        ld.setPassword(ServiceUtils.getMD5String(ld.getPassword()));

        try {
            // 获取连接
            conn = C3P0Utils.getConnection();
            // 开启事务
            conn.setAutoCommit(false);

            // 两个数据表必须同时写入,防止意外中断产生脏数据
            ud.updateUserToAccount(conn, ld);
            ud.updateUserToInformation(conn, user);

            // 事务提交
            conn.commit();
            return true;
        } catch (Throwable e) {
            C3P0Utils.rollbackTransaction(conn);
            return false;
        } finally {
            C3P0Utils.releaseConnection(conn);
        }

    }

    @Override
    public boolean findUserByName(String name) {
        return ud.checkDuplicateNames(name);
    }
}
