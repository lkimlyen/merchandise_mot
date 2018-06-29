package com.demo.merchandisemot.screen.notification;

import com.demo.merchandisemot.app.di.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by MSI on 26/11/2017.
 */

@ActivityScope
@Subcomponent(modules = {NotificationModule.class})
public interface NotificationComponent {
    void inject(NotificationActivity activity);

}
