package com.demo.architect.data.repository.base.other.remote;

import com.demo.architect.data.model.OutletResponse;
import com.demo.architect.data.model.UserLoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by uyminhduc on 10/16/16.
 */

public interface OtherApiInterface {

    @GET("http://merchandisemot.imark.com.vn/WS/api/GetDate?pAppCode=ids")
    Call<String> getDate();

}
