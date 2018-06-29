package com.demo.merchandisemot.screen.notification;

import android.support.annotation.NonNull;
import android.util.Log;

import com.demo.architect.data.model.NotificationEntity;
import com.demo.architect.data.model.offline.NotificationList;
import com.demo.architect.data.model.offline.NotificationModel;
import com.demo.architect.data.repository.base.local.LocalRepository;
import com.demo.architect.domain.BaseUseCase;
import com.demo.architect.domain.GetNotificationUsecase;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by MSI on 26/11/2017.
 */

public class NotificationPresenter implements NotificationContract.Presenter {

    private final String TAG = NotificationPresenter.class.getName();
    private final NotificationContract.View view;
    private final GetNotificationUsecase getNotificationUsecase;
    @Inject
    LocalRepository localRepository;

    @Inject
    NotificationPresenter(@NonNull NotificationContract.View view, GetNotificationUsecase getNotificationUsecase) {
        this.view = view;
        this.getNotificationUsecase = getNotificationUsecase;
    }

    @Inject
    public void setupPresenter() {
        view.setPresenter(this);
    }


    @Override
    public void start() {
        Log.d(TAG, TAG + ".start() called");
        getNotification();
    }

    @Override
    public void stop() {
        Log.d(TAG, TAG + ".stop() called");
    }


    @Override
    public void getNotification() {
        localRepository.findAllNoti().subscribe(new Action1<NotificationList>() {
            @Override
            public void call(NotificationList list) {
                view.showNotification(list);

            }
        });
    }

    @Override
    public void downloadNotification() {
        view.showProgressBar();
        getNotificationUsecase.executeIO(new GetNotificationUsecase.RequestValue(),
                new BaseUseCase.UseCaseCallback<GetNotificationUsecase.ResponseValue,
                        GetNotificationUsecase.ErrorValue>() {
                    @Override
                    public void onSuccess(GetNotificationUsecase.ResponseValue successResponse) {
                        view.hideProgressBar();
                        localRepository.deleteAllNotification().subscribe();
                        for (NotificationEntity entity : successResponse.getEntity()) {
                            NotificationModel model = new NotificationModel(entity.getId(), entity.getSessionCode(),
                                    entity.getDescription(), entity.getTitle(), entity.getDate(), entity.getStatus());
                            localRepository.addNotification(model).subscribe();
                        }
                        getNotification();
                    }

                    @Override
                    public void onError(GetNotificationUsecase.ErrorValue errorResponse) {

                    }
                });
    }
}
