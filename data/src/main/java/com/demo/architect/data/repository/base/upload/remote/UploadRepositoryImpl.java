package com.demo.architect.data.repository.base.upload.remote;

import com.demo.architect.data.model.UploadEntity;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;

//import javax.inject.Singleton;

/**
 * Created by uyminhduc on 10/16/16.
 */
//@Singleton
public class UploadRepositoryImpl implements UploadRepository {

    private final static String TAG = UploadRepositoryImpl.class.getName();

    private UploadApiInterface mRemoteApiInterface;

    public UploadRepositoryImpl(UploadApiInterface mUploadApiInterface) {
        this.mRemoteApiInterface = mUploadApiInterface;
    }


    private void handleUploadImageResponse(Call<UploadEntity> call, Subscriber subscriber) {
        try {
            UploadEntity response = call.execute().body();
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
    public Observable<UploadEntity> uploadImage(final File file, final String appCode, final String sessionCode, final int userTeamID, final String dateTimeDevice, final String date, final String dateServer, final double latitude, final double longitude, final String fileName) {
        return Observable.create(new Observable.OnSubscribe<UploadEntity>() {
            @Override
            public void call(Subscriber<? super UploadEntity> subscriber) {

                handleUploadImageResponse(mRemoteApiInterface.uploadImage(MultipartBody.Part
                                .createFormData("file", file.getName(),
                                        RequestBody.create(MultipartBody.FORM, file)),
                        RequestBody.create(MultipartBody.FORM, appCode),
                        RequestBody.create(MultipartBody.FORM, sessionCode),
                        RequestBody.create(MultipartBody.FORM, userTeamID + ""),
                        RequestBody.create(MultipartBody.FORM, dateTimeDevice),
                        RequestBody.create(MultipartBody.FORM, date),
                        RequestBody.create(MultipartBody.FORM, dateServer),
                        RequestBody.create(MultipartBody.FORM, latitude + ""),
                        RequestBody.create(MultipartBody.FORM, longitude + ""),
                        RequestBody.create(MultipartBody.FORM, fileName + "")), subscriber);
            }
        });
    }
}


