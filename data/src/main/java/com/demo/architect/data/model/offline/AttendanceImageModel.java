package com.demo.architect.data.model.offline;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AttendanceImageModel  extends RealmObject{
    @PrimaryKey
    private int id;
    private int imageId;
    private int attendanceId;
    private String dateCreate;
    private int createBy;

    public AttendanceImageModel(int id, int imageId, int attendanceId, String dateCreate, int createBy) {
        this.id = id;
        this.imageId = imageId;
        this.attendanceId = attendanceId;
        this.dateCreate = dateCreate;
        this.createBy = createBy;
    }

    public int getId() {
        return id;
    }

    public int getImageId() {
        return imageId;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public int getCreateBy() {
        return createBy;
    }
}
