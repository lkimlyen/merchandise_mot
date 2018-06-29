package com.demo.merchandisemot.screen.dashboard;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MSI on 26/11/2017.
 */

@Module
public class DashboardModule {
    private final DashboardContract.View LoginContractView;

    public DashboardModule(DashboardContract.View LoginContractView) {
        this.LoginContractView = LoginContractView;
    }

    @Provides
    @NonNull
    DashboardContract.View provideLoginContractView() {
        return this.LoginContractView;
    }
}

