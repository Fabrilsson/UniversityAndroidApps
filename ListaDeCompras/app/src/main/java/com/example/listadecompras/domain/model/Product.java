package com.example.listadecompras.domain.model;

import android.provider.BaseColumns;

public class Product {

    private int id;
    private String name;
    private double price;

    public Product() {

    }

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static final class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";

        public static final String PRODUCT_NAME_COLUMN = "name";
        public static final String PRODUCT_PRICE_COLUMN = "price";
    }

    public String toString()
    {
        return name;
    }
}
