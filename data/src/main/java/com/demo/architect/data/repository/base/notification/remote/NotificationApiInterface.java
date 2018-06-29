package com.demo.architect.data.repository.base.notification.remote;

import com.demo.architect.data.model.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by uyminhduc on 10/16/16.
 */

public interface NotificationApiInterface {

    @GET("http://merchandisemot.imark.com.vn/WS/api/GetNotification?pAppCode=ids")
    Call<NotificationResponse> getNotification();
}
