package com.demo.architect.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutletEntiy {

    @SerializedName("OutletID")
    @Expose
    private int outletId;

    @SerializedName("OutletCode")
    @Expose
    private String outletCode;

    @SerializedName("OutletName")
    @Expose
    private String outletName;

    @SerializedName("Address")
    @Expose
    private String address;

    @SerializedName("City")
    @Expose
    private String city;

    @SerializedName("OutletType")
    @Expose
    private int outletType;

    public int getOutletId() {
        return outletId;
    }

    public String getOutletCode() {
        return outletCode;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public int getOutletType() {
        return outletType;
    }
}
