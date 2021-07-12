package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.data.model.Task;
import com.example.myapplication.util.Util;

import java.util.ArrayList;
import java.util.List;

public class TasksHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";

    private static final int DATABASE_VERSION = 2;

    public TasksHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TASKS_TABLE = "CREATE TABLE "
                + Task.TaskEntry.TABLE_NAME + " (" +
                Task.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Task.TaskEntry.TASKS_NAME_COLUMN + " TEXT NOT NULL, " +
                Task.TaskEntry.TASKS_ANNOTATION_COLUMN + " TEXT NOT NULL, " +
                Task.TaskEntry.TASKS_STATUS_COLUMN + " INTEGER NOT NULL, " +
                Task.TaskEntry.TASKS_CREATION_DATE_COLUMN + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                Task.TaskEntry.TASKS_FINISH_DATE_COLUMN + " DATETIME DEFAULT NULL" +
                ");";

        db.execSQL(SQL_CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Task.TaskEntry.TABLE_NAME);
        onCreate(db);
    }

    public long insertTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Task.TaskEntry.TASKS_NAME_COLUMN, task.getName().toString());
        values.put(Task.TaskEntry.TASKS_STATUS_COLUMN, task.getStatus());
        values.put(Task.TaskEntry.TASKS_ANNOTATION_COLUMN, task.getAnnotation());
        long id = db.insert(Task.TaskEntry.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public void deleteTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Task.TaskEntry.TABLE_NAME, Task.TaskEntry._ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public int updateTaskStatus(Task task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Task.TaskEntry.TASKS_FINISH_DATE_COLUMN, task.getFinishDate() != null ? task.getFinishDate().toString() : null);
        values.put(Task.TaskEntry.TASKS_STATUS_COLUMN, task.getStatus());

        return db.update(Task.TaskEntry.TABLE_NAME, values, Task.TaskEntry._ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public int updateTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Task.TaskEntry.TASKS_NAME_COLUMN, task.getName().toString());
        values.put(Task.TaskEntry.TASKS_ANNOTATION_COLUMN, task.getAnnotation());
        values.put(Task.TaskEntry.TASKS_CREATION_DATE_COLUMN, task.getCreationDate().toString());

        return db.update(Task.TaskEntry.TABLE_NAME, values, Task.TaskEntry._ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public List<Task> getAllToDoTasks(){
        List<Task> tasks = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Task.TaskEntry.TABLE_NAME + " WHERE " +
                Task.TaskEntry.TASKS_FINISH_DATE_COLUMN + " IS NULL " + " ORDER BY " +
                Task.TaskEntry.TASKS_NAME_COLUMN;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(Task.TaskEntry._ID)));
                task.setName(cursor.getString(cursor.getColumnIndex(Task.TaskEntry.TASKS_NAME_COLUMN)));
                task.setAnnotation(cursor.getString(cursor.getColumnIndex(Task.TaskEntry.TASKS_ANNOTATION_COLUMN)));
                task.setStatus(cursor.getInt(cursor.getColumnIndex(Task.TaskEntry.TASKS_STATUS_COLUMN)));
                task.setCreationDate(Util.strToDateTime(cursor.getString(cursor.getColumnIndex(Task.TaskEntry.TASKS_CREATION_DATE_COLUMN))));
                task.setFinishDate(Util.strToDateTime(cursor.getString(cursor.getColumnIndex(Task.TaskEntry.TASKS_FINISH_DATE_COLUMN))));
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        db.close();

        return  tasks;
    }

    public List<Task> getAllDoneTasks(){
        List<Task> tasks = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Task.TaskEntry.TABLE_NAME + " WHERE " +
                Task.TaskEntry.TASKS_FINISH_DATE_COLUMN + " IS NOT NULL " + " ORDER BY " +
                Task.TaskEntry.TASKS_NAME_COLUMN;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(Task.TaskEntry._ID)));
                task.setStrikedName(cursor.getString(cursor.getColumnIndex(Task.TaskEntry.TASKS_NAME_COLUMN)));
                task.setAnnotation(cursor.getString(cursor.getColumnIndex(Task.TaskEntry.TASKS_ANNOTATION_COLUMN)));
                task.setStatus(cursor.getInt(cursor.getColumnIndex(Task.TaskEntry.TASKS_STATUS_COLUMN)));
                task.setCreationDate(Util.strToDateTime(cursor.getString(cursor.getColumnIndex(Task.TaskEntry.TASKS_CREATION_DATE_COLUMN))));
                task.setFinishDate(Util.strToDateTime(cursor.getString(cursor.getColumnIndex(Task.TaskEntry.TASKS_FINISH_DATE_COLUMN))));
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        db.close();

        return  tasks;
    }
}
