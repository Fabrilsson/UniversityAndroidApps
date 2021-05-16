package com.example.myapplication.data.model;

import android.text.SpannableString;

public class Task {
    private int id, status;
    private SpannableString description;

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

    public SpannableString getDescription() {
        return description;
    }

    public void setDescription(SpannableString description) {
        this.description = description;
    }
}
