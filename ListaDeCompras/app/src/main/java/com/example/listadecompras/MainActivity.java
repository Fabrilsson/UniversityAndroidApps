package com.example.listadecompras;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listadecompras.adapter.ShoppingListAdapter;
import com.example.listadecompras.domain.ProductsHelper;
import com.example.listadecompras.domain.ShoppingListHelper;
import com.example.listadecompras.domain.ShoppingListProductHelper;
import com.example.listadecompras.domain.model.Product;
import com.example.listadecompras.domain.model.ShoppingList;
import com.example.listadecompras.domain.model.ShoppingListProduct;
import com.example.listadecompras.main.AddNewShoppingList;
import com.example.listadecompras.main.EditShoppingList;
import com.example.listadecompras.util.RecyclerViewOnClickListenerHack;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private ShoppingListHelper shoppingListHelper;
    private ProductsHelper productsHelper;
    private ShoppingListProductHelper shoppingListProductHelper;

    private ShoppingListAdapter shoppingListAdapter;

    private RecyclerView mRecyclerView;
    private List<ShoppingList> shoppingLists = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingListHelper = new ShoppingListHelper(this);
        productsHelper = new ProductsHelper(this);
        shoppingListHelper = new ShoppingListHelper(this);
        shoppingListProductHelper = new ShoppingListProductHelper(this);

        shoppingLists.addAll(shoppingListHelper.getAllShoppingLists());
        products.addAll(productsHelper.getAllProducts());
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_shoppingList);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setHasFixedSize(true);

        shoppingListAdapter = new ShoppingListAdapter(shoppingLists);
        shoppingListAdapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(shoppingListAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNewFragment();
                fab.hide();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        fab.show();
    }

    @Override
    public void onClickListener(View view, int position) {
        showActionsDialog(shoppingLists.get(position), position);
        fab.hide();
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
    }

    private void showActionsDialog(final ShoppingList shoppingList, final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a action");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int acao) {
                if (acao == 0) {
                    showEditFragment(shoppingList);
                } else {
                    deleteShoppingList(shoppingList, position);
                }
            }
        });
        builder.show();
    }

    private void deleteShoppingList(ShoppingList shoppingList, int position) {
        shoppingLists.remove(position);
        shoppingListHelper.deleteShoppingList(shoppingList);

        shoppingListProductHelper.deleteShoppingListProductsFromShoppingListId(shoppingList.getId());

        shoppingListAdapter.notifyDataSetChanged();
    }

    private void showAddNewFragment() {
        Fragment newShoppingListFragment = new AddNewShoppingList(shoppingListHelper, shoppingListAdapter, shoppingListProductHelper, products);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.MainActivity, newShoppingListFragment);
        transaction.setReorderingAllowed(true);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showEditFragment(ShoppingList shoppingList) {
        Fragment editShoppingListFragment = new EditShoppingList(shoppingListHelper, shoppingListAdapter, shoppingListProductHelper, products, shoppingList);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.MainActivity, editShoppingListFragment);
        transaction.setReorderingAllowed(true);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}