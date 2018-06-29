package com.demo.architect.domain;

import android.util.Log;

import com.demo.architect.data.helper.Constants;
import com.demo.architect.data.model.UploadEntity;
import com.demo.architect.data.repository.base.upload.remote.UploadRepository;

import java.io.File;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ACER on 7/26/2017.
 */

public class UploadImageUsecase extends BaseUseCase {
    private static final String TAG = UploadImageUsecase.class.getSimpleName();
    private final UploadRepository uploadRepository;

    public UploadImageUsecase(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        File file = ((RequestValue) requestValues).file;
        String sessionCode = ((RequestValue) requestValues).sessionCode;
        int userTeamID = ((RequestValue) requestValues).userTeamID;
        String dateTimeDevice = ((RequestValue) requestValues).dateTimeDevice;
        String date = ((RequestValue) requestValues).date;
        String dateServer = ((RequestValue) requestValues).dateServer;
        double latitude = ((RequestValue) requestValues).latitude;
        double longitude = ((RequestValue) requestValues).longitude;
        String fileName = ((RequestValue) requestValues).fileName;
        return uploadRepository.uploadImage(file, Constants.APP_CODE, sessionCode, userTeamID,
                dateTimeDevice, date, dateServer, latitude, longitude, fileName);
    }

    @Override
    protected Subscriber buildUseCaseSubscriber() {
        return new Subscriber<UploadEntity>() {
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
            public void onNext(UploadEntity data) {
                Log.d(TAG, "onNext: " + String.valueOf(data.getStatus()));
                if (useCaseCallback != null) {
                    int result = data.getId();
                    if (data.getId() > 0 && data.getStatus() == 1) {
                        useCaseCallback.onSuccess(new ResponseValue(result));
                    } else {
                        useCaseCallback.onError(new ErrorValue(data.getDescription()));
                    }
                }
            }
        };
    }

    public static final class RequestValue implements RequestValues {
        private final File file;
        private final String sessionCode;
        private final int userTeamID;
        private final String dateTimeDevice;
        private final String date;
        private final String dateServer;
        private final double latitude;
        private final double longitude;
        private final String fileName;

        public RequestValue(File file, String sessionCode, int userTeamID, String dateTimeDevice, String date, String dateServer, double latitude, double longitude, String fileName) {
            this.file = file;
            this.sessionCode = sessionCode;
            this.userTeamID = userTeamID;
            this.dateTimeDevice = dateTimeDevice;
            this.date = date;
            this.dateServer = dateServer;
            this.latitude = latitude;
            this.longitude = longitude;
            this.fileName = fileName;
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
