package com.qklt.service;

import com.qklt.domain.User;

public interface HomeService {

    /**
     * 修改个人信息
     * @return
     */
    public boolean updateInformation(User user);

    /**
     * 数据库存储头像地址
     * @param user
     * @param portraitName
     * @return
     */
    public User uploadPortrait(User user, String portraitName);

    /**
     * 删除旧头像
     * @param portraitPath
     * @return
     */
    public boolean delOldPortrait(String portraitPath, String portraitName);

}
