package com.demo.architect.data.repository.base.notification.remote;


import com.demo.architect.data.model.NotificationResponse;
import com.demo.architect.data.model.OutletResponse;
import com.demo.architect.data.model.UserLoginResponse;

import rx.Observable;

/**
 * Created by uyminhduc on 10/16/16.
 */

public interface NotificationRepository {
    Observable<NotificationResponse> getNotification();
}
