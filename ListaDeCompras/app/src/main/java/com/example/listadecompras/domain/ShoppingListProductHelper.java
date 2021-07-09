package com.example.listadecompras.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.listadecompras.domain.model.Product;
import com.example.listadecompras.domain.model.ShoppingListProduct;
import com.example.listadecompras.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListProductHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shoppingList.db";

    private static final int DATABASE_VERSION = 1;

    public ShoppingListProductHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE "
                + ShoppingListProduct.ShoppingListProductEntry.TABLE_NAME + " (" +
                ShoppingListProduct.ShoppingListProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ShoppingListProduct.ShoppingListProductEntry.SHOPPINGLISTPRODUCTS_PRODUCTID_COLUMN + " INTEGER NOT NULL, " +
                ShoppingListProduct.ShoppingListProductEntry.SHOPPINGLISTPRODUCTS_SHOPPINGLISTID_COLUMN + " INTEGER NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ShoppingListProduct.ShoppingListProductEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertShoppingList(ShoppingListProduct shoppingListProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ShoppingListProduct.ShoppingListProductEntry.SHOPPINGLISTPRODUCTS_PRODUCTID_COLUMN, shoppingListProduct.getProductId());
        values.put(ShoppingListProduct.ShoppingListProductEntry.SHOPPINGLISTPRODUCTS_SHOPPINGLISTID_COLUMN, shoppingListProduct.getShoppingListId());
        long id = db.insert(ShoppingListProduct.ShoppingListProductEntry.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public void deleteShoppingListProductsFromShoppingListId(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                ShoppingListProduct.ShoppingListProductEntry.TABLE_NAME,
                ShoppingListProduct.ShoppingListProductEntry.SHOPPINGLISTPRODUCTS_SHOPPINGLISTID_COLUMN + " = ?",
                new String[]{String.valueOf(id)}
                );

        db.close();
    }

    public List<Product> getShoppingListProducts(long shoppingListId) {
        List<Product> products = new ArrayList<>();

        String selectQuery = "SELECT P." + Product.ProductEntry._ID + ", P.name, P.price FROM " + Product.ProductEntry.TABLE_NAME + " AS P INNER JOIN " +
                ShoppingListProduct.ShoppingListProductEntry.TABLE_NAME + " AS SLP ON P." + Product.ProductEntry._ID + " == SLP.productId WHERE " +
                ShoppingListProduct.ShoppingListProductEntry.SHOPPINGLISTPRODUCTS_SHOPPINGLISTID_COLUMN + " == "
                + shoppingListId + " ORDER BY " + Product.ProductEntry.PRODUCT_NAME_COLUMN + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(Product.ProductEntry._ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.ProductEntry.PRODUCT_NAME_COLUMN)));
                product.setPrice(Util.strToDouble(cursor.getString(cursor.getColumnIndex(Product.ProductEntry.PRODUCT_PRICE_COLUMN))));
                products.add(product);
            } while (cursor.moveToNext());
        }

        db.close();

        return products;
    }
}
