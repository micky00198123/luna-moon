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
            // ��ȡ����
            conn = C3P0Utils.getConnection();
            // ��������
            conn.setAutoCommit(false);

            // �������ݱ����ͬʱд��,��ֹ�����жϲ���������
            ud.updateUserToAccount(conn, ld);
            ud.updateUserToInformation(conn, user);

            // �����ύ
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
