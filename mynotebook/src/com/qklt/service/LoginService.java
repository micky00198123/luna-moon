package com.qklt.service;

import com.qklt.domain.LoginData;
import com.qklt.domain.User;

public interface LoginService {

    /**
     * ����Ƿ��¼�ɹ�
     * @param ld
     * @return
     */
    public User checkAccount(LoginData ld);

}
