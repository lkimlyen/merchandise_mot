package com.demo.architect.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationEntity {
    @SerializedName("ID")
    @Expose
    private int id;

    @SerializedName("SessionCode")
    @Expose
    private String sessionCode;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("Status")
    @Expose
    private int status;

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
