package com.demo.merchandisemot.screen.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;


import com.demo.merchandisemot.R;
import com.demo.merchandisemot.app.CoreApplication;
import com.demo.merchandisemot.app.base.BaseActivity;
import com.demo.merchandisemot.app.di.Precondition;

import javax.inject.Inject;

/**
 * Created by MSI on 26/11/2017.
 */

public class LoginActivity extends BaseActivity {
    @Inject
    LoginPresenter LoginPresenter;

    LoginFragment fragment;

    public static void start(Context context) {
        start(context, false);
    }

    public static void start(Context context, boolean clearTop) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (clearTop) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initFragment();

        // Create the presenter
        CoreApplication.getInstance().getApplicationComponent()
                .plus(new LoginModule(fragment))
                .inject(this);
    }

    private void initFragment() {
        fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = LoginFragment.newInstance();
            addFragmentToBackStack(fragment, R.id.fragmentContainer);
        }
    }

    private void addFragmentToBackStack(LoginFragment fragment, int frameId) {
        Precondition.checkNotNull(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.findViewById(android.R.id.content).findViewById(R.id.fragmentContainer).setPadding(0, 0, 0, 0);
    }
}
