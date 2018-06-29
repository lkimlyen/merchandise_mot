package com.demo.architect.data.repository.base.upload.remote;

import com.demo.architect.data.model.UploadEntity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by uyminhduc on 10/16/16.
 */

public interface UploadApiInterface {

    @Multipart
    @POST("http://merchandisemot.imark.com.vn/WS/UploadImage/UploadFile")
    Call<UploadEntity> uploadImage(
            @Part MultipartBody.Part file,
            @Part("pAppCode") RequestBody appCode,
            @Part("pSessionCode") RequestBody sessionCode,
            @Part("pUserTeamID") RequestBody userTeamID,
            @Part("pDateTimeDevice") RequestBody dateTimeDevice,
            @Part("pDate") RequestBody date,
            @Part("pDateServer") RequestBody dateServer,
            @Part("pLatGPS") RequestBody latitude,
            @Part("pLongGPS") RequestBody longitude,
            @Part("pFileName") RequestBody fileName);

}
