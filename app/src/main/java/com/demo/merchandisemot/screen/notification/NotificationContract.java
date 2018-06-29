package com.demo.merchandisemot.screen.notification;


import com.demo.architect.data.model.offline.NotificationList;
import com.demo.merchandisemot.app.base.BasePresenter;
import com.demo.merchandisemot.app.base.BaseView;

/**
 * Created by MSI on 26/11/2017.
 */

public interface NotificationContract {
    interface View extends BaseView<Presenter> {
        void showNotification(NotificationList list);
    }

    interface Presenter extends BasePresenter {
        void getNotification();
        void downloadNotification();
    }
}
