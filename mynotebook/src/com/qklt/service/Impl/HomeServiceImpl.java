package com.qklt.service.Impl;

import com.qklt.dao.impl.UserDAOImpl;
import com.qklt.domain.User;
import com.qklt.service.HomeService;

import java.io.File;

public class HomeServiceImpl implements HomeService {

    private UserDAOImpl ud = new UserDAOImpl();

    @Override
    public boolean updateInformation(User user) {
        return ud.updateUserInformation(user);
    }

    @Override
    public User uploadPortrait(User user, String portraitName) {
        if(portraitName == null) {
            return user;
        }
        user.setPortrait(portraitName);
        ud.updateUserInformation(user);
        return user;
    }

    @Override
    public boolean delOldPortrait(String portraitPath, String portraitName) {
        if(portraitName == null || portraitPath == null) {
            return false;
        }
        // Ä¬ÈÏÍ·Ïñ²»É¾³ý
        if("1.png".equals(portraitName)) {
            return true;
        }
        File delFile = new File(portraitPath);
        return delFile.delete();
    }

}
