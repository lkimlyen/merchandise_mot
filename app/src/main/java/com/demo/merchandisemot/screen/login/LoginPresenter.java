package com.demo.merchandisemot.screen.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.demo.architect.data.repository.base.local.LocalRepository;
import com.demo.architect.domain.BaseUseCase;
import com.demo.architect.domain.GetOutletUsecase;
import com.demo.architect.domain.LoginUsecase;
import com.demo.merchandisemot.manager.OutletManager;
import com.demo.merchandisemot.manager.UserManager;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by MSI on 26/11/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final String TAG = LoginPresenter.class.getName();
    private final LoginContract.View view;
    private final GetOutletUsecase getOutletUsecase;
    @Inject
    LocalRepository localRepository;

    LoginUsecase loginUsecase;

    @Inject
    LoginPresenter(@NonNull LoginContract.View view, GetOutletUsecase getOutletUsecase, LoginUsecase loginUsecase) {
        this.view = view;
        this.getOutletUsecase = getOutletUsecase;
        this.loginUsecase = loginUsecase;
    }

    @Inject
    public void setupPresenter() {
        view.setPresenter(this);
    }


    @Override
    public void start() {
        Log.d(TAG, TAG + ".start() called");
    }

    @Override
    public void stop() {
        Log.d(TAG, TAG + ".stop() called");
    }

    @Override
    public void login(String username, String password) {
        view.showProgressBar();
        loginUsecase.executeIO(new LoginUsecase.RequestValue(username, password), new BaseUseCase.UseCaseCallback
                <LoginUsecase.ResponseValue, LoginUsecase.ErrorValue>() {
            @Override
            public void onSuccess(LoginUsecase.ResponseValue successResponse) {

                Log.d(TAG, new Gson().toJson(successResponse.getEntity()));

                //Save user entity to shared preferences
                UserManager.getInstance().setUser(successResponse.getEntity());
                getOulet(successResponse.getEntity().getUserTeamId());

            }

            @Override
            public void onError(LoginUsecase.ErrorValue errorResponse) {
                view.showError(errorResponse.getDescription());
                view.hideProgressBar();
            }
        });
    }

    private void getOulet(int userTeamId) {
        getOutletUsecase.executeIO(new GetOutletUsecase.RequestValue(userTeamId),
                new BaseUseCase.UseCaseCallback<GetOutletUsecase.ResponseValue,
                        GetOutletUsecase.ErrorValue>() {
                    @Override
                    public void onSuccess(GetOutletUsecase.ResponseValue successResponse) {
                        view.hideProgressBar();
                        OutletManager.getInstance().setOutlet(successResponse.getEntity());
                        view.loginSuccess();
                    }

                    @Override
                    public void onError(GetOutletUsecase.ErrorValue errorResponse) {

                    }
                });
    }

}
