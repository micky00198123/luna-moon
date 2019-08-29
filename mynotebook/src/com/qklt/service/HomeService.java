package com.qklt.service;

import com.qklt.domain.User;

public interface HomeService {

    /**
     * �޸ĸ�����Ϣ
     * @return
     */
    public boolean updateInformation(User user);

    /**
     * ���ݿ�洢ͷ���ַ
     * @param user
     * @param portraitName
     * @return
     */
    public User uploadPortrait(User user, String portraitName);

    /**
     * ɾ����ͷ��
     * @param portraitPath
     * @return
     */
    public boolean delOldPortrait(String portraitPath, String portraitName);

}
