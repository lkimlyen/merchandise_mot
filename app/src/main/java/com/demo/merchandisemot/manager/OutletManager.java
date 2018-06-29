package com.demo.merchandisemot.manager;

import com.demo.architect.data.helper.SharedPreferenceHelper;
import com.demo.architect.data.model.OutletEntiy;
import com.demo.architect.data.model.UserLoginResponse;
import com.demo.merchandisemot.app.CoreApplication;

public class OutletManager {
    private OutletEntiy outletEntiy;
    private static OutletManager instance;

    public static OutletManager getInstance() {
        if (instance == null) {
            instance = new OutletManager();
        }
        return instance;
    }

    public void setOutlet(OutletEntiy user) {
        outletEntiy = user;
        SharedPreferenceHelper.getInstance(CoreApplication.getInstance()).pushOutletObject(outletEntiy);
    }

    public OutletEntiy getOutlet() {
        if (outletEntiy == null) {
            outletEntiy = SharedPreferenceHelper.getInstance(CoreApplication.getInstance()).getOutletObject();
        }
        return outletEntiy;
    }


}
