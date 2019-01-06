package com.nandohidayat.app.ayamku;

import java.util.Objects;

public class CheckoutModel {
    String name;
    double price;
    int many;

    public CheckoutModel(String name, double price, int many) {
        this.name = name;
        this.price = price;
        this.many = many;
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

    public int getMany() {
        return many;
    }

    public void setMany(int many) {
        this.many = many;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckoutModel that = (CheckoutModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
