package com.nandohidayat.app.ayamku;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ayam implements Serializable {
    private String image;
    private String name;
    private double price;
    private String desc;

    public Ayam(String image, String name, double price, String desc) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.desc = desc;
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
