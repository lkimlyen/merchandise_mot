package com.demo.architect.data.repository.base.account.remote;


import com.demo.architect.data.model.AttendanceResponse;
import com.demo.architect.data.model.OutletResponse;
import com.demo.architect.data.model.UserLoginResponse;

import rx.Observable;

/**
 * Created by uyminhduc on 10/16/16.
 */

public interface AccountRepository {
    Observable<UserLoginResponse> login(String appCode, String userType, String username, String password);

    Observable<OutletResponse> getSimpleOutlet(String appCode, int userTeamId);

    Observable<AttendanceResponse> attendanceTracking(String appCode, String sessionCode, int userTeamId,
                                                      String time, String type, double latitude, double longitude,
                                                      int number, String dateServer);

    Observable<AttendanceResponse> addImageForAttenadence(String appCode, String sessionCode, int attendanceId, int imageId);
}
