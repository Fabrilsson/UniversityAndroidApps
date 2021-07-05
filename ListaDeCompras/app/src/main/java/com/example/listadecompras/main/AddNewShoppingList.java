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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.listadecompras.R;
import com.example.listadecompras.adapter.AddNewShoppingListAdapter;
import com.example.listadecompras.domain.ProductsHelper;
import com.example.listadecompras.domain.model.Product;
import com.example.listadecompras.domain.model.ShoppingList;
import com.example.listadecompras.util.RecyclerViewOnClickListenerHack;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AddNewShoppingList extends Fragment implements RecyclerViewOnClickListenerHack {

    private AddNewShoppingListAdapter addNewShoppingListAdapter;
    private List<Product> products;
    private List<Product> shoppingListProducts;

    private RecyclerView rv;

    public AddNewShoppingList(List<Product> products) {
        this.products = products;
        this.shoppingListProducts = new ArrayList<>();

        this.addNewShoppingListAdapter = new AddNewShoppingListAdapter(shoppingListProducts);
        this.addNewShoppingListAdapter.setRecyclerViewOnClickListenerHack(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(getActivity() , R.layout.simple_spinner_dropdown_item, products);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Product p = (Product)spinner.getSelectedItem();

                        shoppingListProducts.add(0, p);
                        addNewShoppingListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        rv = (RecyclerView)view.findViewById(R.id.rv_productList_new_list);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rv.setHasFixedSize(true);

        rv.setAdapter(addNewShoppingListAdapter);
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