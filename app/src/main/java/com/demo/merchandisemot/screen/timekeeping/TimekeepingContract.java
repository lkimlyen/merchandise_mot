package com.demo.merchandisemot.screen.timekeeping;


import android.graphics.Bitmap;

import com.demo.merchandisemot.app.base.BasePresenter;
import com.demo.merchandisemot.app.base.BaseView;

/**
 * Created by MSI on 26/11/2017.
 */

public interface TimekeepingContract {
    interface View extends BaseView<Presenter> {
        void showDateServer(long time);
        void showError(String message);
        void showSuccess(String message);
        void backToDashboard();
    }

    interface Presenter extends BasePresenter {
        void getDateServer();
        void attendanceTracking(double latitude, double longitude, String type, String dateServer);
        void uploadImage(double latitude, double longitude, String path, String dateServer);
    }
}
