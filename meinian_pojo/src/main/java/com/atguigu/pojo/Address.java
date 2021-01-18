package com.atguigu.pojo;

import java.io.Serializable;

public class Address implements Serializable {
    private int id;
    private String addressName;
    private String lng;
    private String lat;

    public Address() {
    }

    public Address(int id, String addressName, String lng, String lat) {
        this.id = id;
        this.addressName = addressName;
        this.lng = lng;
        this.lat = lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

}
