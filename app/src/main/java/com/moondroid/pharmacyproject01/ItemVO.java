package com.moondroid.pharmacyproject01;

public class ItemVO {
    private String address;
    private String name;
    private String tell;
    private String lat;
    private String lng;

    public ItemVO() {
    }

    public ItemVO(String address, String name, String tell, String lat, String lng) {
        this.address = address;
        this.name = name;
        this.tell = tell;
        this.lat = lat;
        this.lng = lng;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getTell() {
        return tell;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
