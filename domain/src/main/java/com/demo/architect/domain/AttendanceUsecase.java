package com.demo.architect.domain;

import android.util.Log;

import com.demo.architect.data.helper.Constants;
import com.demo.architect.data.model.AttendanceResponse;
import com.demo.architect.data.repository.base.account.remote.AccountRepository;

import rx.Observable;
import rx.Subscriber;


public class AttendanceUsecase extends BaseUseCase {
    private static final String TAG = AttendanceUsecase.class.getSimpleName();
    private final AccountRepository remoteRepository;

    public AttendanceUsecase(AccountRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        String sessionCode = ((RequestValue) requestValues).sessionCode;
        int userTeamId = ((RequestValue) requestValues).userTeamId;
        String time = ((RequestValue) requestValues).time;
        String type = ((RequestValue) requestValues).type;
        double latitude = ((RequestValue) requestValues).latitude;
        double longitude = ((RequestValue) requestValues).longitude;
        int number = ((RequestValue) requestValues).number;
        String dateServer = ((RequestValue) requestValues).dateServer;
        return remoteRepository.attendanceTracking(Constants.APP_CODE, sessionCode,
                userTeamId, time, type, latitude, longitude, number, dateServer);
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
                    int result = data.getAttendanceId();
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
        private final int userTeamId;
        private final String time;
        private final String type;
        private final double latitude;
        private final double longitude;
        private final int number;
        private final String dateServer;


        public RequestValue(String sessionCode, int userTeamId, String time, String type, double latitude, double longitude, int number, String dateServer) {
            this.sessionCode = sessionCode;
            this.userTeamId = userTeamId;
            this.time = time;
            this.type = type;
            this.latitude = latitude;
            this.longitude = longitude;
            this.number = number;
            this.dateServer = dateServer;
        }
    }

    public static final class ResponseValue implements ResponseValues {
        private int id;

        public ResponseValue(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
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
