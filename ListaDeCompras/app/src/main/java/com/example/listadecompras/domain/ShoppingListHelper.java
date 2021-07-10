package com.example.listadecompras.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.listadecompras.domain.model.ShoppingList;
import com.example.listadecompras.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shoppingList.db";

    private static final int DATABASE_VERSION = 1;

    public ShoppingListHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE "
                + ShoppingList.ShoppingListEntry.TABLE_NAME + " (" +
                ShoppingList.ShoppingListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ShoppingList.ShoppingListEntry.SHOPPINGLIST_NAME_COLUMN + " TEXT NOT NULL, " +
                ShoppingList.ShoppingListEntry.SHOPPINGLIST_DATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                ShoppingList.ShoppingListEntry.SHOPPINGLIST_TOTALPRICE_COLUMN + " REAL NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ShoppingList.ShoppingListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertShoppingList(ShoppingList shoppingList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ShoppingList.ShoppingListEntry.SHOPPINGLIST_NAME_COLUMN, shoppingList.getName());
        values.put(ShoppingList.ShoppingListEntry.SHOPPINGLIST_TOTALPRICE_COLUMN, shoppingList.getTotalPrice());
        long id = db.insert(ShoppingList.ShoppingListEntry.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public void deleteShoppingList(ShoppingList shoppingList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ShoppingList.ShoppingListEntry.TABLE_NAME, ShoppingList.ShoppingListEntry._ID + " = ?",
                new String[]{String.valueOf(shoppingList.getId())});
        db.close();
    }

    public int updateShoppingList(ShoppingList shoppingList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ShoppingList.ShoppingListEntry.SHOPPINGLIST_NAME_COLUMN, shoppingList.getName());
        values.put(ShoppingList.ShoppingListEntry.SHOPPINGLIST_TOTALPRICE_COLUMN, shoppingList.getTotalPrice());

        return db.update(ShoppingList.ShoppingListEntry.TABLE_NAME, values, ShoppingList.ShoppingListEntry._ID + " = ?",
                new String[]{String.valueOf(shoppingList.getId())});
    }

    public List<ShoppingList> getAllShoppingLists() {
        List<ShoppingList> shoppingLists = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + ShoppingList.ShoppingListEntry.TABLE_NAME + " ORDER BY " +
                ShoppingList.ShoppingListEntry.SHOPPINGLIST_DATE_TIME + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(cursor.getInt(cursor.getColumnIndex(ShoppingList.ShoppingListEntry._ID)));
                shoppingList.setName(cursor.getString(cursor.getColumnIndex(ShoppingList.ShoppingListEntry.SHOPPINGLIST_NAME_COLUMN)));
                shoppingList.setDataHora(Util.strToDateTime(cursor.getString(cursor.getColumnIndex(ShoppingList.ShoppingListEntry.SHOPPINGLIST_DATE_TIME))));
                shoppingList.setTotalPrice(Util.strToDouble(cursor.getString(cursor.getColumnIndex(ShoppingList.ShoppingListEntry.SHOPPINGLIST_TOTALPRICE_COLUMN))));
                shoppingLists.add(shoppingList);
            } while (cursor.moveToNext());
        }

        db.close();

        return  shoppingLists;
    }
}
