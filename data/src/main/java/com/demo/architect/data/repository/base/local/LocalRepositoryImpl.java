package com.demo.architect.data.repository.base.local;

import com.demo.architect.data.model.MessageModel;
import com.demo.architect.data.model.offline.AttendanceImageModel;
import com.demo.architect.data.model.offline.AttendanceModel;
import com.demo.architect.data.model.offline.ImageModel;
import com.demo.architect.data.model.offline.NotificationList;
import com.demo.architect.data.model.offline.NotificationModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;

public class LocalRepositoryImpl implements LocalRepository {

    DatabaseRealm databaseRealm;

    public LocalRepositoryImpl() {
        databaseRealm = new DatabaseRealm();
    }

    @Override
    public Observable<String> add(final MessageModel model) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    databaseRealm.add(model);

                    subscriber.onNext(model.getUuid());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<List<MessageModel>> findAll() {
        return Observable.create(new Observable.OnSubscribe<List<MessageModel>>() {
            @Override
            public void call(Subscriber<? super List<MessageModel>> subscriber) {
                try {
                    List<MessageModel> models = databaseRealm.findAll(MessageModel.class);

                    subscriber.onNext(models);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<NotificationModel> addNotification(final NotificationModel model) {
        return Observable.create(new Observable.OnSubscribe<NotificationModel>() {
            @Override
            public void call(Subscriber<? super NotificationModel> subscriber) {
                try {
                    databaseRealm.addNotficationItemAsync(model);

                    subscriber.onNext(model);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<NotificationList> findAllNoti() {
        return Observable.create(new Observable.OnSubscribe<NotificationList>() {
            @Override
            public void call(Subscriber<? super NotificationList> subscriber) {
                try {
                    NotificationList models = databaseRealm.getListNoti();
                    subscriber.onNext(models);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<NotificationModel> deleteAllNotification() {
        return Observable.create(new Observable.OnSubscribe<NotificationModel>() {
            @Override
            public void call(Subscriber<? super NotificationModel> subscriber) {
                try {
                    Set<Integer> countersToDelete = new HashSet<>();

                    List<NotificationModel> list = databaseRealm.findAll(NotificationModel.class);
                    for (NotificationModel item : list){
                        countersToDelete.add(item.getId());
                    }
                    databaseRealm.deleteItemsAsync(countersToDelete);
                    subscriber.onNext(new NotificationModel());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<String> addAttendanceModel(final AttendanceModel model) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    databaseRealm.add(model);

                    subscriber.onNext(model.getId()+"");
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<Integer> addImageModel(final ImageModel model) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    databaseRealm.addImage(model);

                    subscriber.onNext(model.getId());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<String> addAttendanceImageModel(final AttendanceImageModel model) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    databaseRealm.add(model);

                    subscriber.onNext(model.getId()+"");
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
