package com.demo.architect.domain;

import android.util.Log;

import com.demo.architect.data.helper.Constants;
import com.demo.architect.data.model.UserLoginResponse;
import com.demo.architect.data.repository.base.account.remote.AccountRepository;

import rx.Observable;
import rx.Subscriber;


public class LoginUsecase extends BaseUseCase {
    private static final String TAG = LoginUsecase.class.getSimpleName();
    private final AccountRepository remoteRepository;

    public LoginUsecase(AccountRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        String userName = ((RequestValue) requestValues).userName;
        String password = ((RequestValue) requestValues).password;
        return remoteRepository.login(Constants.APP_CODE, Constants.USER_TYPE,userName, password);
    }

    @Override
    protected Subscriber buildUseCaseSubscriber() {
        return new Subscriber<UserLoginResponse>() {
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
            public void onNext(UserLoginResponse data) {
                Log.d(TAG, "onNext: " + String.valueOf(data.getStatus()));
                if (useCaseCallback != null) {
                    UserLoginResponse result = data;
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
        public final String userName;
        public final String password;

        public RequestValue(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
    }

    public static final class ResponseValue implements ResponseValues {
        private UserLoginResponse entity;

        public ResponseValue(UserLoginResponse entity) {
            this.entity = entity;
        }

        public UserLoginResponse getEntity() {
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
