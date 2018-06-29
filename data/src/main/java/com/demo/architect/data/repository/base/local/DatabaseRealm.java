package com.demo.architect.data.repository.base.local;

import com.demo.architect.data.model.offline.ImageModel;
import com.demo.architect.data.model.offline.NotificationList;
import com.demo.architect.data.model.offline.NotificationModel;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DatabaseRealm {

    public DatabaseRealm() {

    }

    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    public <T extends RealmObject> T add(T model) {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        realm.copyToRealm(model);
        realm.commitTransaction();
        return model;
    }

    public <T extends RealmObject> List<T> findAll(Class<T> clazz) {
        return getRealmInstance().where(clazz).findAll();
    }

    public void close() {
        getRealmInstance().close();
    }

    public void addNotficationItemAsync(final NotificationModel item) {
        Realm realm = getRealmInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                NotificationModel.create(realm, item);
            }
        });
    }

    public NotificationList getListNoti(){
        Realm realm = getRealmInstance();
        return realm.where(NotificationList.class).findFirst();
    }

    public <T extends RealmObject> void deleteAllItems(Class<T> clazz) {
        Realm realm = getRealmInstance();
        final RealmResults<T> results = realm.where(clazz).findAll();

// All changes to data must happen in a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteItemsAsync(Collection<Integer> ids) {
        Realm realm = getRealmInstance();
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

    public void addImage(final ImageModel item) {
        Realm realm = getRealmInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                item.setId(ImageModel.id(realm)+1);
                realm.copyToRealm(item);
            }
        });
    }

}
