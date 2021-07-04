package com.example.listadecompras.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.listadecompras.R;
import com.example.listadecompras.adapter.AddNewShoppingListAdapter;
import com.example.listadecompras.domain.model.Product;
import com.example.listadecompras.domain.model.ShoppingList;
import com.example.listadecompras.util.RecyclerViewOnClickListenerHack;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AddNewShoppingList extends Fragment implements RecyclerViewOnClickListenerHack {

    private List<Product> products;

    private RecyclerView rv;

    public AddNewShoppingList(List<Product> list) {
        products = list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);

        ArrayList<String> test = new ArrayList<>();

        for (Product product:
             products) {
            test.add(product.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity() , R.layout.simple_spinner_dropdown_item, test);

        spinner.setAdapter(adapter);

        AddNewShoppingListAdapter addNewShoppingListAdapter = new AddNewShoppingListAdapter(products);
        addNewShoppingListAdapter.setRecyclerViewOnClickListenerHack(this);
        //rv.setAdapter(addNewShoppingListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_shopping_list, container, false);

        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        //showActionsDialog(lembreteList.get(position), position);
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
    }
}