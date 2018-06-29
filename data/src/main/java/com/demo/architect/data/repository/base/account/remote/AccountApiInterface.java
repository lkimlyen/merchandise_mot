package com.demo.architect.data.repository.base.account.remote;

import com.demo.architect.data.model.AttendanceResponse;
import com.demo.architect.data.model.OutletResponse;
import com.demo.architect.data.model.UserLoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by uyminhduc on 10/16/16.
 */

public interface AccountApiInterface {

    @FormUrlEncoded
    @POST("http://merchandisemot.imark.com.vn/WS/api/Login")
    Call<UserLoginResponse> login(@Field("pAppCode") String appCode,
                                  @Field("pUserType") String userType,
                                  @Field("pUserName") String userName,
                                  @Field("pPassWord") String password);

    @FormUrlEncoded
    @POST("http://merchandisemot.imark.com.vn/WS/api/GetSimpleOutlet")
    Call<OutletResponse> getSimpleOutlet(@Field("pAppCode") String appCode,
                                         @Field("pUserTeamID") int userTeamId);

    @FormUrlEncoded
    @POST("http://merchandisemot.imark.com.vn/WS/api/AttendanceTracking")
    Call<AttendanceResponse> attendanceTracking(@Field("pAppCode") String appCode,
                                                @Field("pSessionCode") String sessionCode,
                                                @Field("pUserTeamID") int userTeamId,
                                                @Field("pAttendanceDateTime") String time,
                                                @Field("pTimePointType") String type,
                                                @Field("pLatGPS") double latitude,
                                                @Field("pLongGPS") double longitude,
                                                @Field("pNumberPG") int number,
                                                @Field("pDateServer") String dateServer  );

    @FormUrlEncoded
    @POST("http://merchandisemot.imark.com.vn/WS/api/AddImageForAttenadence")
    Call<AttendanceResponse> addImageForAttenadence(@Field("pAppCode") String appCode,
                                                @Field("pSessionCode") String sessionCode,
                                                @Field("pAttendanceID") int attendanceId,
                                                @Field("pImageID") int imageId);


}
