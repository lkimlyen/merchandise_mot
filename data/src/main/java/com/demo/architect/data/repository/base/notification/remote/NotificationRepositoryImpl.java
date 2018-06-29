package com.demo.architect.data.repository.base.notification.remote;
import com.demo.architect.data.model.NotificationResponse;
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
public class NotificationRepositoryImpl implements NotificationRepository {

    private final static String TAG = NotificationRepositoryImpl.class.getName();

    private NotificationApiInterface mRemoteApiInterface;

    public NotificationRepositoryImpl(NotificationApiInterface mNotificationApiInterface) {
        this.mRemoteApiInterface = mNotificationApiInterface;
    }


    private void handleNotificationResponse(Call<NotificationResponse> call, Subscriber subscriber) {
        try {
            NotificationResponse response = call.execute().body();
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
    public Observable<NotificationResponse> getNotification() {
        return Observable.create(new Observable.OnSubscribe<NotificationResponse>() {
            @Override
            public void call(Subscriber<? super NotificationResponse> subscriber) {
                handleNotificationResponse(mRemoteApiInterface.getNotification(), subscriber);
            }
        });
    }
}


