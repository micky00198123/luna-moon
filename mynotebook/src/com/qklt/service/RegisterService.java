package com.qklt.service;

import com.qklt.domain.LoginData;

public interface RegisterService {

    /**
     * �˺�д�����ݿ�,������Ƿ�ע��ɹ�
     * @param ld
     * @return
     */
    public boolean registerAccount(LoginData ld);

    /**
     * ʹ���û��������û�
     * @param name
     * @return
     */
    public boolean findUserByName(String name);

}
