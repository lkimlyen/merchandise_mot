package com.demo.architect.domain;

import android.util.Log;

import com.demo.architect.data.helper.Constants;
import com.demo.architect.data.model.NotificationEntity;
import com.demo.architect.data.model.NotificationResponse;
import com.demo.architect.data.model.UserLoginResponse;
import com.demo.architect.data.repository.base.account.remote.AccountRepository;
import com.demo.architect.data.repository.base.notification.remote.NotificationRepository;

import java.util.List;

import rx.Observable;
import rx.Subscriber;


public class GetNotificationUsecase extends BaseUseCase {
    private static final String TAG = GetNotificationUsecase.class.getSimpleName();
    private final NotificationRepository remoteRepository;

    public GetNotificationUsecase(NotificationRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return remoteRepository.getNotification();
    }

    @Override
    protected Subscriber buildUseCaseSubscriber() {
        return new Subscriber<NotificationResponse>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.toString());
                if (useCaseCallback != null) {
                    useCaseCallback.onError(new ErrorValue(e.getMessage()));
                }
            }

            @Override
            public void onNext(NotificationResponse data) {
                Log.d(TAG, "onNext: " + String.valueOf(data.getStatus()));
                if (useCaseCallback != null) {
                    List<NotificationEntity> result = data.getList();
                    if (result != null && data.getStatus() == 1) {
                        useCaseCallback.onSuccess(new ResponseValue(result));
                    } else {
                        useCaseCallback.onError(new ErrorValue(data.getDescription()));
                    }
                }
            }
        };
    }

    public static final class RequestValue implements RequestValues {
    }

    public static final class ResponseValue implements ResponseValues {
        private List<NotificationEntity> entity;

        public ResponseValue(List<NotificationEntity> entity) {
            this.entity = entity;
        }

        public List<NotificationEntity> getEntity() {
            return entity;
        }
    }

    public static final class ErrorValue implements ErrorValues {
        private final String description;

        public ErrorValue(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
