package com.example.listadecompras.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.listadecompras.domain.model.Product;
import com.example.listadecompras.domain.model.ShoppingList;
import com.example.listadecompras.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ProductsHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shoppingList.db";

    private static final int DATABASE_VERSION = 1;

    public ProductsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE "
                + Product.ProductEntry.TABLE_NAME + " (" +
                Product.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Product.ProductEntry.PRODUCT_NAME_COLUMN + " TEXT NOT NULL, " +
                Product.ProductEntry.PRODUCT_PRICE_COLUMN + " REAL NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Product.ProductEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Product.ProductEntry.PRODUCT_NAME_COLUMN, product.getName());
        values.put(Product.ProductEntry.PRODUCT_PRICE_COLUMN, product.getPrice());
        long id = db.insert(Product.ProductEntry.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Product.ProductEntry.TABLE_NAME, Product.ProductEntry._ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Product.ProductEntry.PRODUCT_NAME_COLUMN, product.getName());
        values.put(Product.ProductEntry.PRODUCT_PRICE_COLUMN, product.getPrice());

        return db.update(Product.ProductEntry.TABLE_NAME, values, Product.ProductEntry._ID + " = ?",
                new String[]{String.valueOf(product.getId())});
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Product.ProductEntry.TABLE_NAME + " ORDER BY " +
                Product.ProductEntry.PRODUCT_NAME_COLUMN + " DESC";

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
