package com.demo.merchandisemot.screen.dashboard;

import android.support.annotation.NonNull;
import android.util.Log;

import com.demo.architect.data.model.OutletEntiy;
import com.demo.architect.data.model.UserLoginResponse;
import com.demo.architect.data.repository.base.local.LocalRepository;
import com.demo.architect.domain.BaseUseCase;
import com.demo.architect.domain.LoginUsecase;
import com.demo.merchandisemot.manager.OutletManager;
import com.demo.merchandisemot.manager.UserManager;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by MSI on 26/11/2017.
 */

public class DashboardPresenter implements DashboardContract.Presenter {

    private final String TAG = DashboardPresenter.class.getName();
    private final DashboardContract.View view;

    @Inject
    LocalRepository localRepository;

    LoginUsecase loginUsecase;

    @Inject
    DashboardPresenter(@NonNull DashboardContract.View view, LoginUsecase loginUsecase) {
        this.view = view;
        this.loginUsecase = loginUsecase;
    }

    @Inject
    public void setupPresenter() {
        view.setPresenter(this);
    }


    @Override
    public void start() {
        Log.d(TAG, TAG + ".start() called");
        getOutlet();
        getUserInfo();
    }

    @Override
    public void stop() {
        Log.d(TAG, TAG + ".stop() called");
    }


    @Override
    public void getOutlet() {
        view.showInfoOutlet(OutletManager.getInstance().getOutlet());
    }

    @Override
    public void getUserInfo() {
        view.showUserInfo(UserManager.getInstance().getUser().getDisplayName());
    }
}
