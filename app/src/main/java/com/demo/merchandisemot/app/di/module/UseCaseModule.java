package com.demo.merchandisemot.app.di.module;



import com.demo.architect.data.repository.base.account.remote.AccountRepository;
import com.demo.architect.data.repository.base.notification.remote.NotificationRepository;
import com.demo.architect.data.repository.base.other.remote.OtherApiInterface;
import com.demo.architect.data.repository.base.other.remote.OtherRepository;
import com.demo.architect.data.repository.base.upload.remote.UploadRepository;
import com.demo.architect.domain.AddImageAttendanceUsecase;
import com.demo.architect.domain.AttendanceUsecase;
import com.demo.architect.domain.GetDateUsecase;
import com.demo.architect.domain.GetNotificationUsecase;
import com.demo.architect.domain.GetOutletUsecase;
import com.demo.architect.domain.LoginUsecase;
import com.demo.architect.domain.UploadImageUsecase;

import dagger.Module;
import dagger.Provides;

/**
 * Created by uyminhduc on 12/16/16.
 */
@Module
public class UseCaseModule {
    public UseCaseModule() {
    }

    @Provides
    LoginUsecase provideLoginUsecase(AccountRepository remoteRepository) {
        return new LoginUsecase(remoteRepository);
    }

    @Provides
    GetOutletUsecase provideGetOutletUsecase(AccountRepository remoteRepository) {
        return new GetOutletUsecase(remoteRepository);
    }

    @Provides
    GetNotificationUsecase provideGetNotificationUsecase(NotificationRepository remoteRepository) {
        return new GetNotificationUsecase(remoteRepository);
    }

    @Provides
    GetDateUsecase provideGetDateUsecase(OtherRepository remoteRepository) {
        return new GetDateUsecase(remoteRepository);
    }

    @Provides
    AttendanceUsecase provideAttendanceUsecase(AccountRepository remoteRepository) {
        return new AttendanceUsecase(remoteRepository);
    }

    @Provides
    UploadImageUsecase provideUploadImageUsecase(UploadRepository remoteRepository) {
        return new UploadImageUsecase(remoteRepository);
    }

    @Provides
    AddImageAttendanceUsecase provideAddImageAttendanceUsecase(AccountRepository remoteRepository) {
        return new AddImageAttendanceUsecase(remoteRepository);
    }
}

