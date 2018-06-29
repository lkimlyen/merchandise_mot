package com.demo.architect.data.repository.base.local;

import com.demo.architect.data.model.MessageModel;
import com.demo.architect.data.model.offline.AttendanceImageModel;
import com.demo.architect.data.model.offline.AttendanceModel;
import com.demo.architect.data.model.offline.ImageModel;
import com.demo.architect.data.model.offline.NotificationList;
import com.demo.architect.data.model.offline.NotificationModel;

import java.util.List;

import rx.Observable;

public interface LocalRepository {

    Observable<String> add(MessageModel model);

    Observable<List<MessageModel>> findAll();

    Observable<NotificationModel> addNotification(NotificationModel model);

    Observable<NotificationList> findAllNoti();

    Observable<NotificationModel> deleteAllNotification();

    Observable<String> addAttendanceModel(AttendanceModel model);

    Observable<Integer> addImageModel(ImageModel model);

    Observable<String> addAttendanceImageModel(AttendanceImageModel model);

}
