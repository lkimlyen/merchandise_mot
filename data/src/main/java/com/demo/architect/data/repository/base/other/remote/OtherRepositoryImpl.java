package com.demo.architect.data.repository.base.other.remote;
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
public class OtherRepositoryImpl implements OtherRepository {

    private final static String TAG = OtherRepositoryImpl.class.getName();

    private OtherApiInterface mRemoteApiInterface;

    public OtherRepositoryImpl(OtherApiInterface mOtherApiInterface) {
        this.mRemoteApiInterface = mOtherApiInterface;
    }


    private void handleStringResponse(Call<String> call, Subscriber subscriber) {
        try {
            String response = call.execute().body();
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
    public Observable<String> getDate() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                handleStringResponse(mRemoteApiInterface.getDate(), subscriber);
            }
        });
    }
}


