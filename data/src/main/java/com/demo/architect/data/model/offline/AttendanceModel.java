package com.demo.architect.data.model.offline;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AttendanceModel extends RealmObject{
    @PrimaryKey
    private int id;
    private String location;
    private String timeServer;
    private String dateCreate;
    private int createBy;

    public AttendanceModel(int id, String location, String timeServer, String dateCreate, int createBy) {
        this.id = id;
        this.location = location;
        this.timeServer = timeServer;
        this.dateCreate = dateCreate;
        this.createBy = createBy;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getTimeServer() {
        return timeServer;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public int getCreateBy() {
        return createBy;
    }
}
