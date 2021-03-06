package com.example.listadecompras.main;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.listadecompras.R;
import com.example.listadecompras.adapter.AddNewShoppingListAdapter;
import com.example.listadecompras.adapter.ShoppingListAdapter;
import com.example.listadecompras.domain.ProductsHelper;
import com.example.listadecompras.domain.ShoppingListHelper;
import com.example.listadecompras.domain.ShoppingListProductHelper;
import com.example.listadecompras.domain.model.Product;
import com.example.listadecompras.domain.model.ShoppingList;
import com.example.listadecompras.domain.model.ShoppingListProduct;
import com.example.listadecompras.util.RecyclerViewOnClickListenerHack;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNewShoppingList extends Fragment implements RecyclerViewOnClickListenerHack {

    private ShoppingListHelper shoppingListHelper;
    private ShoppingListProductHelper shoppingListProductHelper;

    private ShoppingListAdapter shoppingListAdapter;
    private AddNewShoppingListAdapter addNewShoppingListAdapter;
    private List<Product> products;
    private List<Product> shoppingListProducts;

    private RecyclerView rv;

    private EditText shoppingListPrice;

    public AddNewShoppingList(
            ShoppingListHelper shoppingListHelper,
            ShoppingListAdapter shoppingListAdapter,
            ShoppingListProductHelper shoppingListProductHelper,
            List<Product> products)
    {
        this.shoppingListHelper = shoppingListHelper;
        this.shoppingListProductHelper = shoppingListProductHelper;

        this.shoppingListAdapter = shoppingListAdapter;

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

        shoppingListPrice = view.findViewById(R.id.shoppingListPrice);

        products.add(0, new Product(0, "Select", 0));

        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(getActivity() , R.layout.simple_spinner_dropdown_item, products);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                        Product p = (Product)spinner.getSelectedItem();

                        if(p.getId() != 0) {
                            shoppingListProducts.add(0, p);
                            addNewShoppingListAdapter.notifyDataSetChanged();

                            updateScreenListPrice();
                        }
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

        Button saveButton = (Button)view.findViewById(R.id.saveShoppingList);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewShoppingList(view);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_shopping_list, container, false);

        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        showActionsDialog(shoppingListProducts.get(position), position);
    }

    private void showActionsDialog(final Product product, final int position) {
        CharSequence colors[] = new CharSequence[]{"Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose a action");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int acao) {
                deleteShoppingListProduct(product, position);
            }
        });
        builder.show();
    }

    private void deleteShoppingListProduct(Product shoppingListProduct, int position) {
        shoppingListProducts.remove(position);
        shoppingListProductHelper.deleteProduct(shoppingListProduct);
        addNewShoppingListAdapter.notifyDataSetChanged();

        double totalPrice = shoppingListProducts.stream().mapToDouble(Product::getPrice).sum();

        shoppingListPrice.setText(String.valueOf(totalPrice));
    }

    private void addNewShoppingList(View view){
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setDataHora(new Date());

        EditText listNameView = (EditText)view.findViewById(R.id.ListName);
        String shoppingListName = listNameView == null ? "" : listNameView.getText().toString();

        shoppingList.setName(shoppingListName);
        List<Product> productsFromRV = addNewShoppingListAdapter.getShoppingListProducts();
        shoppingList.setProducts(productsFromRV);

        double totalPrice = productsFromRV.stream().mapToDouble(Product::getPrice).sum();
        shoppingList.setTotalPrice(totalPrice);

        long shoppingListId = shoppingListHelper.insertShoppingList(shoppingList);

        for (Product product:
                productsFromRV) {
            shoppingListProductHelper.insertShoppingListProduct(new ShoppingListProduct(shoppingListId, product.getId()));
        }

        shoppingList.setId(shoppingListId);

        shoppingListAdapter.addShoppingList(shoppingList);
        shoppingListAdapter.notifyDataSetChanged();
    }

    private void updateScreenListPrice(){
        double totalPrice = shoppingListProducts.stream().mapToDouble(Product::getPrice).sum();

        shoppingListPrice.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
    }
}