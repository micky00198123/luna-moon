package com.qklt.service.Impl;

import com.qklt.dao.impl.UserDAOImpl;
import com.qklt.domain.LoginData;
import com.qklt.domain.User;
import com.qklt.service.LoginService;
import com.qklt.util.ServiceUtils;

public class LoginServiceImpl implements LoginService {

    private UserDAOImpl ud = new UserDAOImpl();

    @Override
    public User checkAccount(LoginData ld) {
        ld.setPassword(ServiceUtils.getMD5String(ld.getPassword()));
        return ud.checkAccount(ld) ?
                ud.getUserByName(ld.getUserName()) : null;
    }
}
