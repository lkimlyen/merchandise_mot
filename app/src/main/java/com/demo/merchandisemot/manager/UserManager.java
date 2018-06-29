package com.demo.merchandisemot.manager;

import com.demo.architect.data.helper.SharedPreferenceHelper;
import com.demo.architect.data.model.UserLoginResponse;
import com.demo.merchandisemot.app.CoreApplication;

public class UserManager {
    private UserLoginResponse userEntity;
    private static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setUser(UserLoginResponse user) {
        userEntity = user;
        SharedPreferenceHelper.getInstance(CoreApplication.getInstance()).pushUserObject(userEntity);
    }

    public UserLoginResponse getUser() {
        if (userEntity == null) {
            userEntity = SharedPreferenceHelper.getInstance(CoreApplication.getInstance()).getUserObject();
        }
        return userEntity;
    }


}
