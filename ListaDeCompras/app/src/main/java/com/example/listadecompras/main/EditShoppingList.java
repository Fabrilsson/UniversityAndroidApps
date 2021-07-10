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

import com.example.listadecompras.R;
import com.example.listadecompras.adapter.AddNewShoppingListAdapter;
import com.example.listadecompras.adapter.EditShoppingListAdapter;
import com.example.listadecompras.adapter.ShoppingListAdapter;
import com.example.listadecompras.domain.ShoppingListHelper;
import com.example.listadecompras.domain.ShoppingListProductHelper;
import com.example.listadecompras.domain.model.Product;
import com.example.listadecompras.domain.model.ShoppingList;
import com.example.listadecompras.domain.model.ShoppingListProduct;
import com.example.listadecompras.util.RecyclerViewOnClickListenerHack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditShoppingList extends Fragment implements RecyclerViewOnClickListenerHack {

    private ShoppingListHelper shoppingListHelper;
    private ShoppingListProductHelper shoppingListProductHelper;

    private ShoppingListAdapter shoppingListAdapter;
    private EditShoppingListAdapter editShoppingListAdapter;
    private List<Product> products;
    private List<Product> shoppingListProducts;

    private RecyclerView rv;

    private ShoppingList shoppingList;

    private EditText shoppingListPrice;

    public EditShoppingList(
            ShoppingListHelper shoppingListHelper,
            ShoppingListAdapter shoppingListAdapter,
            ShoppingListProductHelper shoppingListProductHelper,
            List<Product> products,
            ShoppingList shoppingList) {
        this.shoppingListHelper = shoppingListHelper;
        this.shoppingListProductHelper = shoppingListProductHelper;

        this.shoppingListAdapter = shoppingListAdapter;

        this.products = products;

        this.shoppingList = shoppingList;

        this.shoppingListProducts = shoppingListProductHelper.getShoppingListProducts(shoppingList.getId());

        this.editShoppingListAdapter = new EditShoppingListAdapter(shoppingListProducts, shoppingList);
        this.editShoppingListAdapter.setRecyclerViewOnClickListenerHack(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shoppingListPrice = view.findViewById(R.id.shoppingListPrice);

        updateScreenListPrice();

        products.add(0, new Product(0, "Select", 0));

        EditText listName = (EditText)view.findViewById(R.id.ListName);

        listName.setText(shoppingList.getName());

        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(getActivity() , R.layout.simple_spinner_dropdown_item, products);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Product p = (Product)spinner.getSelectedItem();

                        if(p.getId() != 0) {
                            shoppingListProducts.add(0, p);

                            ShoppingListProduct shoppingListProduct = new ShoppingListProduct(shoppingList.getId(), p.getId());

                            shoppingListProductHelper.insertShoppingListProduct(shoppingListProduct);
                            editShoppingListAdapter.notifyDataSetChanged();

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

        rv.setAdapter(editShoppingListAdapter);

        Button saveButton = (Button)view.findViewById(R.id.saveShoppingList);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shoppingList.setDataHora(new Date());

                EditText listNameView = (EditText)view.findViewById(R.id.ListName);
                String shoppingListName = listNameView == null ? "" : listNameView.getText().toString();

                shoppingList.setName(shoppingListName);
                List<Product> productsFromRV = editShoppingListAdapter.getShoppingListProducts();
                shoppingList.setProducts(productsFromRV);

                double totalPrice = productsFromRV.stream().mapToDouble(Product::getPrice).sum();
                shoppingList.setTotalPrice(totalPrice);

                shoppingListHelper.updateShoppingList(shoppingList);

                shoppingListAdapter.updateShoppingList(shoppingList);
                shoppingListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_shopping_list, container, false);
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
        editShoppingListAdapter.notifyDataSetChanged();

        double totalPrice = shoppingListProducts.stream().mapToDouble(Product::getPrice).sum();

        shoppingListPrice.setText(String.valueOf(totalPrice));
    }

    private void updateScreenListPrice(){
        double totalPrice = shoppingListProducts.stream().mapToDouble(Product::getPrice).sum();

        shoppingListPrice.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
    }
}