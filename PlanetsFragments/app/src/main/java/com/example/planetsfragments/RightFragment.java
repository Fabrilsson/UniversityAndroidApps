package com.example.planetsfragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RightFragment extends Fragment {

    private int images_id[] = {
            R.drawable.mercurio,
            R.drawable.venus,
            R.drawable.terra,
            R.drawable.marte,
            R.drawable.jupiter,
            R.drawable.saturno,
            R.drawable.urano,
            R.drawable.netuno,
            R.drawable.plutao,
    };

    private ImageView imPlanet;
    private TextView tvDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        imPlanet = (ImageView) view.findViewById(R.id.imPlanet);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        imPlanet.setScaleType(ImageView.ScaleType.FIT_XY);

        Configuration config = getResources().getConfiguration();

        if(config.orientation != Configuration.ORIENTATION_LANDSCAPE){
            String description = getArguments().getString("description");
            int position = getArguments().getInt("pos");
            imPlanet.setImageResource(images_id[position]);
            tvDescription.setText(description);
        }

        return view;
    }

    public void setContent(int pos, String description){
        imPlanet.setImageResource(images_id[pos]);
        tvDescription.setText(description);
    }
}