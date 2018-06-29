package com.demo.architect.data.repository.base.account.remote;
import com.demo.architect.data.model.AttendanceResponse;
import com.demo.architect.data.model.OutletResponse;
import com.demo.architect.data.model.UserLoginResponse;

import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;

//import javax.inject.Singleton;

/**
 * Created by uyminhduc on 10/16/16.
 */
//@Singleton
public class AccountRepositoryImpl implements AccountRepository {

    private final static String TAG = AccountRepositoryImpl.class.getName();

    private AccountApiInterface mRemoteApiInterface;

    public AccountRepositoryImpl(AccountApiInterface mAccountApiInterface) {
        this.mRemoteApiInterface = mAccountApiInterface;
    }


    private void handleUserLoginResponse(Call<UserLoginResponse> call, Subscriber subscriber) {
        try {
            UserLoginResponse response = call.execute().body();
            if (!subscriber.isUnsubscribed()) {
                if (response != null) {
                    subscriber.onNext(response);
                } else {
                    subscriber.onError(new Exception("Network Error!"));
                }
                subscriber.onCompleted();
            }
        } catch (Exception e) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onError(e);
                subscriber.onCompleted();
            }
        }
    }

    private void handleOutletResponse(Call<OutletResponse> call, Subscriber subscriber) {
        try {
            OutletResponse response = call.execute().body();
            if (!subscriber.isUnsubscribed()) {
                if (response != null) {
                    subscriber.onNext(response);
                } else {
                    subscriber.onError(new Exception("Network Error!"));
                }
                subscriber.onCompleted();
            }
        } catch (Exception e) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onError(e);
                subscriber.onCompleted();
            }
        }
    }

    private void handleAttendanceResponse(Call<AttendanceResponse> call, Subscriber subscriber) {
        try {
            AttendanceResponse response = call.execute().body();
            if (!subscriber.isUnsubscribed()) {
                if (response != null) {
                    subscriber.onNext(response);
                } else {
                    subscriber.onError(new Exception("Network Error!"));
                }
                subscriber.onCompleted();
            }
        } catch (Exception e) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onError(e);
                subscriber.onCompleted();
            }
        }
    }

    @Override
    public Observable<UserLoginResponse> login(final String appCode, final String userType, final String username, final String password) {
        return Observable.create(new Observable.OnSubscribe<UserLoginResponse>() {
            @Override
            public void call(Subscriber<? super UserLoginResponse> subscriber) {
                handleUserLoginResponse(mRemoteApiInterface.login(appCode, userType, username, password), subscriber);
            }
        });
    }

    @Override
    public Observable<OutletResponse> getSimpleOutlet(final String appCode, final int userTeamId) {
        return Observable.create(new Observable.OnSubscribe<OutletResponse>() {
            @Override
            public void call(Subscriber<? super OutletResponse> subscriber) {
                handleOutletResponse(mRemoteApiInterface.getSimpleOutlet(appCode,
                        userTeamId), subscriber);
            }
        });
    }

    @Override
    public Observable<AttendanceResponse> attendanceTracking(final String appCode, final String sessionCode,
                                                             final int userTeamId, final String time, final String type,
                                                             final double latitude, final double longitude, final int number,
                                                             final String dateServer) {
        return Observable.create(new Observable.OnSubscribe<AttendanceResponse>() {
            @Override
            public void call(Subscriber<? super AttendanceResponse> subscriber) {
                handleAttendanceResponse(mRemoteApiInterface.attendanceTracking(appCode,
                        sessionCode, userTeamId,time, type, latitude, longitude, number,
                        dateServer), subscriber);
            }
        });
    }

    @Override
    public Observable<AttendanceResponse> addImageForAttenadence(final String appCode, final String sessionCode, final int attendanceId, final int imageId) {
        return Observable.create(new Observable.OnSubscribe<AttendanceResponse>() {
            @Override
            public void call(Subscriber<? super AttendanceResponse> subscriber) {
                handleAttendanceResponse(mRemoteApiInterface.addImageForAttenadence(appCode,
                        sessionCode, attendanceId, imageId), subscriber);
            }
        });
    }
}


