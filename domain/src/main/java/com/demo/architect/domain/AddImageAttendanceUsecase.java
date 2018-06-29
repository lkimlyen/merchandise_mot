package com.demo.architect.domain;

import android.util.Log;

import com.demo.architect.data.helper.Constants;
import com.demo.architect.data.model.AttendanceResponse;
import com.demo.architect.data.model.UploadEntity;
import com.demo.architect.data.repository.base.account.remote.AccountRepository;
import com.demo.architect.data.repository.base.upload.remote.UploadRepository;

import java.io.File;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ACER on 7/26/2017.
 */

public class AddImageAttendanceUsecase extends BaseUseCase {
    private static final String TAG = AddImageAttendanceUsecase.class.getSimpleName();
    private final AccountRepository uploadRepository;

    public AddImageAttendanceUsecase(AccountRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        String sessionCode = ((RequestValue) requestValues).sessionCode;
        int attendanceId = ((RequestValue) requestValues).attendanceId;
        int imageId = ((RequestValue) requestValues).imageId;

        return uploadRepository.addImageForAttenadence(Constants.APP_CODE, sessionCode, attendanceId,
                imageId);
    }

    @Override
    protected Subscriber buildUseCaseSubscriber() {
        return new Subscriber<AttendanceResponse>() {
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
            public void onNext(AttendanceResponse data) {
                Log.d(TAG, "onNext: " + String.valueOf(data.getStatus()));
                if (useCaseCallback != null) {
                    int result = data.getAttendanceImageId();
                    if (result > 0 && data.getStatus() == 1) {
                        useCaseCallback.onSuccess(new ResponseValue(result));
                    } else {
                        useCaseCallback.onError(new ErrorValue(data.getDescription()));
                    }
                }
            }
        };
    }

    public static final class RequestValue implements RequestValues {
        private final String sessionCode;
        private final int attendanceId;
        private final int imageId;

        public RequestValue(String sessionCode, int attendanceId, int imageId) {
            this.sessionCode = sessionCode;
            this.attendanceId = attendanceId;
            this.imageId = imageId;
        }
    }

    public static final class ResponseValue implements ResponseValues {
        private final int id;

        public ResponseValue(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public static final class ErrorValue implements ErrorValues {

        private final String Description;

        public ErrorValue(String description) {
            Description = description;
        }

        public String getDescription() {
            return Description;
        }
    }

}
