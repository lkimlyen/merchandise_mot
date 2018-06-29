package com.demo.merchandisemot.screen.dashboard;

import com.demo.merchandisemot.app.di.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by MSI on 26/11/2017.
 */

@ActivityScope
@Subcomponent(modules = {DashboardModule.class})
public interface DashboardComponent {
    void inject(DashboardActivity activity);

}
