package com.demo.architect.domain;

import android.util.Log;

import com.demo.architect.data.model.NotificationEntity;
import com.demo.architect.data.model.NotificationResponse;
import com.demo.architect.data.repository.base.notification.remote.NotificationRepository;
import com.demo.architect.data.repository.base.other.remote.OtherRepository;

import java.util.List;

import rx.Observable;
import rx.Subscriber;


public class GetDateUsecase extends BaseUseCase {
    private static final String TAG = GetDateUsecase.class.getSimpleName();
    private final OtherRepository remoteRepository;

    public GetDateUsecase(OtherRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return remoteRepository.getDate();
    }

    @Override
    protected Subscriber buildUseCaseSubscriber() {
        return new Subscriber<String>() {
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
            public void onNext(String data) {
                Log.d(TAG, "onNext: " + String.valueOf(data));
                if (useCaseCallback != null) {
                    if (data != null) {
                        useCaseCallback.onSuccess(new ResponseValue(data));
                    } else {
                        useCaseCallback.onError(new ErrorValue(data));
                    }
                }
            }
        };
    }

    public static final class RequestValue implements RequestValues {
    }

    public static final class ResponseValue implements ResponseValues {
        private String date;

        public ResponseValue(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
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
