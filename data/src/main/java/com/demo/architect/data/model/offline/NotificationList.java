package com.demo.architect.data.model.offline;

import android.app.Notification;

import io.realm.RealmList;
import io.realm.RealmObject;

public class NotificationList extends RealmObject {
    @SuppressWarnings("unused")
    private RealmList<NotificationModel> itemList;

    public RealmList<NotificationModel> getItemList() {
        return itemList;
    }
}
