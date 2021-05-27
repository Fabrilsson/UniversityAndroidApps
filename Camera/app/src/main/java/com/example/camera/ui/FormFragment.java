package com.example.camera.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.camera.MainActivity;
import com.example.camera.R;
import com.example.camera.model.Form;

public class FormFragment extends Fragment {

    private static final String DESCRIBABLE_KEY = "describable_key";
    private Form form;

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView name = view.findViewById(R.id.txtName);
        TextView email = view.findViewById(R.id.txtEmail);
        TextView phone = view.findViewById(R.id.txtPhone);
        Button button = view.findViewById(R.id.returnButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0 ){
                    getFragmentManager().popBackStack();
                }
            }
        });

        form = (Form)getArguments().getSerializable(DESCRIBABLE_KEY);

        name.setText(form.getName());
        email.setText(form.getEmail());
        phone.setText(form.getPhone());

        ImageView imageView = view.findViewById(R.id.imageview);
        imageView.setImageBitmap(form.getBitmap());
    }

    public static FormFragment newInstance(Form form) {
        FormFragment formFragment = new FormFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, form);
        formFragment.setArguments(bundle);
        return formFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_form, container, false);
    }
}