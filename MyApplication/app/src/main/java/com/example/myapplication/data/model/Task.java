package com.example.myapplication.data.model;

import android.provider.BaseColumns;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;

import androidx.annotation.Nullable;

import java.util.Date;

public class Task {
    private int id;
    private int status;
    private SpannableString name;
    private String annotation;
    private Date creationDate;
    private @Nullable Date finishDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SpannableString getName() {
        return name;
    }

    public String getNameString() {
        return name.toString();
    }

    public void setName(SpannableString name) {
        this.name = name;
    }

    public void setName(String name){
        this.name = new SpannableString(name);
    }

    public void setStrikedName(String name){
        SpannableString string = new SpannableString(name);
        string.setSpan(new StrikethroughSpan(), 0, string.length(), 0);

        this.name = string;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(@Nullable Date finishDate) {
        this.finishDate = finishDate;
    }

    public static final class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";

        public static final String TASKS_NAME_COLUMN = "name";
        public static final String TASKS_ANNOTATION_COLUMN = "annotation";
        public static final String TASKS_STATUS_COLUMN = "statusId";
        public static final String TASKS_CREATION_DATE_COLUMN = "creationDate";
        public static final String TASKS_FINISH_DATE_COLUMN = "finishDate";
    }

    public Task(){

    }

    public Task(int status, SpannableString name, String annotation, Date creationDate){
        this.status = status;
        this.name = name;
        this.annotation = annotation;
        this.creationDate = creationDate;
    }

    public Task(int status, String name, String annotation, Date creationDate){
        this.status = status;
        this.name = new SpannableString(name);
        this.annotation = annotation;
        this.creationDate = creationDate;
    }
}
