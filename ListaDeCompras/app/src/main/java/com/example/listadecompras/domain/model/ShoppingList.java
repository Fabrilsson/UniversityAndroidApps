package com.example.listadecompras.domain.model;

import android.provider.BaseColumns;

import java.util.Date;
import java.util.List;

public class ShoppingList {

    private long id;
    private String name;
    private List<Product> products;
    private Date dataHora;
    private double totalPrice;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static final class ShoppingListEntry implements BaseColumns {
        public static final String TABLE_NAME = "shopingLists";

        public static final String SHOPPINGLIST_NAME_COLUMN = "name";
        public static final String SHOPPINGLIST_TOTALPRICE_COLUMN = "totalPrice";
        public static final String SHOPPINGLIST_DATE_TIME = "date";
    }


}
