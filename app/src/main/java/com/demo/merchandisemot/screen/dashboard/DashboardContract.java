package com.demo.merchandisemot.screen.dashboard;


import com.demo.architect.data.model.OutletEntiy;
import com.demo.merchandisemot.app.base.BasePresenter;
import com.demo.merchandisemot.app.base.BaseView;

/**
 * Created by MSI on 26/11/2017.
 */

public interface DashboardContract {
    interface View extends BaseView<Presenter> {
        void showInfoOutlet(OutletEntiy outlet);
        void showUserInfo(String fullName);
    }

    interface Presenter extends BasePresenter {
        void getOutlet();
        void getUserInfo();
    }
}
