package com.example.camera.model;

import android.graphics.Bitmap;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.widget.ImageView;

import java.io.Serializable;

public class Form implements Serializable {

    private String Name;
    private String Email;
    private String Phone;

    private Bitmap Bitmap;

    public Form(String name, String email, String phone, Bitmap bitmap){
        Name = name;
        Email = email;
        Phone = phone;
        Bitmap = bitmap;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public Bitmap getBitmap() {
        return Bitmap;
    }
}
