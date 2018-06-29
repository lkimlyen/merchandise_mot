package com.demo.architect.data.model.offline;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NotificationModel extends RealmObject {
    @PrimaryKey
    private int id;

    private String sessionCode;

    private String description;

    private String title;

    private String date;

    private int status;

    public NotificationModel() {
    }

    public NotificationModel(int id, String sessionCode, String description, String title, String date, int status) {
        this.id = id;
        this.sessionCode = sessionCode;
        this.description = description;
        this.title = title;
        this.date = date;
        this.status = status;
    }

    public static void delete(Realm realm, int id) {
        NotificationModel item = realm.where(NotificationModel.class).equalTo("id", id).findFirst();
        // Otherwise it has been deleted already.
        if (item != null) {
            item.deleteFromRealm();
        }
    }

    public static void create(Realm realm, NotificationModel item) {
        NotificationList parent = realm.where(NotificationList.class).findFirst();
        RealmList<NotificationModel> items = parent.getItemList();
        NotificationModel model = realm.copyToRealmOrUpdate(item);
        items.add(model);
    }

    public int getId() {
        return id;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }
}
