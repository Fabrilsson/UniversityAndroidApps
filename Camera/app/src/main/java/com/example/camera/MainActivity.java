package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.camera.model.Form;
import com.example.camera.ui.FormFragment;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void sendContent(View view){
        EditText name = findViewById(R.id.nameText);
        EditText email = findViewById(R.id.emailText);
        EditText phone = findViewById(R.id.phoneText);

        String nameTxt = name.getText().toString();
        String emailTxt = email.getText().toString();
        String phoneTxt = phone.getText().toString();

        Boolean error = false;

        if(nameTxt.isEmpty()) {
            name.setError("Name must not be blank");
            error = true;
        }

        if(emailTxt.isEmpty()) {
            email.setError("E-mail must not be blank");
            error = true;
        }

        if(phoneTxt.isEmpty()) {
            phone.setError("Phone must not be blank");
            error = true;
        }

        if (error)
            return;

        Form form = new Form(nameTxt, emailTxt, phoneTxt, image);

        Fragment fragment = FormFragment.newInstance(form);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            image = imageBitmap;
        }
    }

}