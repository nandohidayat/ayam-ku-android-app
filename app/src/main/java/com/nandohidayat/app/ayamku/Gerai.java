package com.nandohidayat.app.ayamku;

public class Gerai {
    String kd_gerai;
    String nama;
    String phone;
    String sms;
    String latitude;
    String longitude;

    public Gerai(String kd_gerai, String nama, String phone, String sms, String latitude, String longitude) {
        this.kd_gerai = kd_gerai;
        this.nama = nama;
        this.phone = phone;
        this.sms = sms;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getKd_gerai() {
        return kd_gerai;
    }

    public void setKd_gerai(String kd_gerai) {
        this.kd_gerai = kd_gerai;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
