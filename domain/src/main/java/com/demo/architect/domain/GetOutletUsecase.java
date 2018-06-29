package com.demo.architect.domain;

import android.util.Log;

import com.demo.architect.data.helper.Constants;
import com.demo.architect.data.model.OutletEntiy;
import com.demo.architect.data.model.OutletResponse;
import com.demo.architect.data.model.UserLoginResponse;
import com.demo.architect.data.repository.base.account.remote.AccountRepository;

import rx.Observable;
import rx.Subscriber;


public class GetOutletUsecase extends BaseUseCase {
    private static final String TAG = GetOutletUsecase.class.getSimpleName();
    private final AccountRepository remoteRepository;

    public GetOutletUsecase(AccountRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        int userTeamId = ((RequestValue) requestValues).userTeamId;
        return remoteRepository.getSimpleOutlet(Constants.APP_CODE, userTeamId);
    }

    @Override
    protected Subscriber buildUseCaseSubscriber() {
        return new Subscriber<OutletResponse>() {
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
            public void onNext(OutletResponse data) {
                Log.d(TAG, "onNext: " + String.valueOf(data.getStatus()));
                if (useCaseCallback != null) {
                    OutletEntiy result = data.getOutlet();
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
        public final int userTeamId;

        public RequestValue(int userTeamId) {
            this.userTeamId = userTeamId;
        }
    }

    public static final class ResponseValue implements ResponseValues {
        private OutletEntiy entity;

        public ResponseValue(OutletEntiy entity) {
            this.entity = entity;
        }

        public OutletEntiy getEntity() {
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
