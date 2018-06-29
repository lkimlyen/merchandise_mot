package com.demo.architect.data.helper;

import com.demo.architect.data.model.offline.NotificationModel;

import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by uyminhduc on 4/5/17.
 */

public class RealmHelper {
    private static Realm mRealm = Realm.getDefaultInstance();

    public static void addNotficationItemAsync(final NotificationModel item) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                NotificationModel.create(realm, item);
            }
        });
    }

    public static <T extends RealmObject> RealmResults<T> getAll(Class<T> clazz) {
        return mRealm.where(clazz).findAll();
    }


//    public static int getMaxId() {
//        int nextId = 0;
//        Number id = mRealm.where(QRCode.class).max("id");
//        // If id is null, set it to 1, else set increment it by 1
//        nextId = (id == null) ? 0 : id.intValue();
//        return nextId;
//    }

    public static void deleteNotificationItemAsync(final int id) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                NotificationModel.delete(realm, id);
            }
        });
    }


    public static void deleteItemsAsync(Realm realm, Collection<Integer> ids) {
        // Create an new array to avoid concurrency problem.
        final Integer[] idsToDelete = new Integer[ids.size()];
        ids.toArray(idsToDelete);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Integer id : idsToDelete) {
                    NotificationModel.delete(realm, id);
                }
            }
        });
    }

    public static <T extends RealmObject> void deleteAllItems(Class<T> clazz) {
        final RealmResults<T> results = mRealm.where(clazz).findAll();

// All changes to data must happen in a transaction
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }
}
