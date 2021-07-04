package com.example.listadecompras.domain;

import android.provider.BaseColumns;

public class Product {

    private int id;
    private String name;
    private double price;

    public Product() {

    }

    public static final class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";

        public static final String PRODUCT_NAME_COLUMN = "name";
        public static final String PRODUCT_PRICE_COLUMN = "price";
    }
}
