package com.qklt.service;

import com.qklt.domain.LoginData;
import com.qklt.domain.User;

public interface LoginService {

    /**
     * 检查是否登录成功
     * @param ld
     * @return
     */
    public User checkAccount(LoginData ld);

}
