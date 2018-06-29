package com.demo.architect.data.model.offline;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ImageModel extends RealmObject {
    @PrimaryKey
    private int id;
    private String location;
    private int createBy;
    private String timeServer;
    private String dateCreate;
    private String path;
    private int serverId;
    private int status;

    public ImageModel(String location, int createBy, String timeServer, String dateCreate, String path, int serverId,int status) {

        this.location = location;
        this.createBy = createBy;
        this.timeServer = timeServer;
        this.dateCreate = dateCreate;
        this.path = path;
        this.serverId = serverId;
        this.status = status;
    }

    public static int id(Realm realm) {
        int nextId = 0;
        Number maxValue = realm.where(ImageModel.class).max("id");
        // If id is null, set it to 1, else set increment it by 1
        nextId = (maxValue == null) ? 0 : maxValue.intValue();
        return nextId;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public int getCreateBy() {
        return createBy;
    }

    public String getTimeServer() {
        return timeServer;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public String getPath() {
        return path;
    }

    public int getServerId() {
        return serverId;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public void setTimeServer(String timeServer) {
        this.timeServer = timeServer;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
