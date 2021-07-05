package com.example.listadecompras.domain.model;

import android.provider.BaseColumns;

public class ShoppingListProduct {
    private int id;
    private int productId;
    private int shoppingListId;

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getShoppingListId() {
        return shoppingListId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public static final class ShoppingListProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "shopingListProducts";

        public static final String SHOPPINGLISTPRODUCTS_PRODUCTID_COLUMN = "productId";
        public static final String SHOPPINGLISTPRODUCTS_SHOPPINGLISTID_COLUMN = "shoppingListId";
    }
}
