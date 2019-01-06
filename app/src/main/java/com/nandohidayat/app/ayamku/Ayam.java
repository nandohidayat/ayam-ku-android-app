package com.nandohidayat.app.ayamku;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ayam implements Serializable {
    private String kd_brg;
    private String image;
    private String name;
    private double price;
    private String desc;

    public Ayam(String kd_brg, String image, String name, double price, String desc) {
        this.kd_brg = kd_brg;
        this.image = image;
        this.name = name;
        this.price = price;
        this.desc = desc;
    }

    public String getKd_brg() {
        return kd_brg;
    }

    public void setKd_brg(String kd_brg) {
        this.kd_brg = kd_brg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
