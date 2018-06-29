package com.demo.merchandisemot.screen.timekeeping;

import com.demo.merchandisemot.app.di.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by MSI on 26/11/2017.
 */

@ActivityScope
@Subcomponent(modules = {TimekeepingModule.class})
public interface TimekeepingComponent {
    void inject(TimekeepingActivity activity);

}
