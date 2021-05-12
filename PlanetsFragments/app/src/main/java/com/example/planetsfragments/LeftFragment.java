package com.example.planetsfragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LeftFragment extends Fragment {

    private ListView lvPlanets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left, container, false);

        lvPlanets = (ListView)view.findViewById(R.id.lvPlanets);
        lvPlanets.setOnItemClickListener((adapter, v, position, id) ->{
            handleSelection(position);
        });

        return view;
    }

    private void handleSelection(int position){
        String description = lvPlanets.getItemAtPosition(position).toString();

        Configuration config = getResources().getConfiguration();

        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            FragmentManager fm = getFragmentManager();
            RightFragment rf = (RightFragment)fm.findFragmentById(R.id.rightFragment);
            rf.setContent(position, description);
        }
        else{
            RightFragment rf = new RightFragment();
            Bundle args = new Bundle();
            args.putInt("pos", position);
            args.putString("description", description);
            rf.setArguments(args);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(android.R.id.content, rf);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}