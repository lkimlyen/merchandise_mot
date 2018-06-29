package com.demo.merchandisemot.app.di.component;



import com.demo.merchandisemot.app.CoreApplication;
import com.demo.merchandisemot.app.base.BaseActivity;
import com.demo.merchandisemot.app.base.BaseFragment;
import com.demo.merchandisemot.app.di.module.ApplicationModule;
import com.demo.merchandisemot.app.di.module.NetModule;
import com.demo.merchandisemot.app.di.module.RepositoryModule;
import com.demo.merchandisemot.app.di.module.UseCaseModule;
import com.demo.merchandisemot.screen.dashboard.DashboardComponent;
import com.demo.merchandisemot.screen.dashboard.DashboardModule;
import com.demo.merchandisemot.screen.login.LoginComponent;
import com.demo.merchandisemot.screen.login.LoginModule;
import com.demo.merchandisemot.screen.notification.NotificationComponent;
import com.demo.merchandisemot.screen.notification.NotificationModule;
import com.demo.merchandisemot.screen.timekeeping.TimekeepingComponent;
import com.demo.merchandisemot.screen.timekeeping.TimekeepingModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by uyminhduc on 12/16/16.
 */

@Singleton
@Component(modules = {ApplicationModule.class,
        NetModule.class,
        UseCaseModule.class,
        RepositoryModule.class})
public interface ApplicationComponent {

    void inject(CoreApplication application);

    void inject(BaseActivity activity);

    void inject(BaseFragment fragment);

    LoginComponent plus(LoginModule loginModule);
    DashboardComponent plus(DashboardModule module);
    NotificationComponent plus(NotificationModule module);
    TimekeepingComponent plus(TimekeepingModule module);
}
