package com.example.listadecompras;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listadecompras.adapter.ShoppingListAdapter;
import com.example.listadecompras.domain.ProductsHelper;
import com.example.listadecompras.domain.ShoppingListHelper;
import com.example.listadecompras.domain.model.Product;
import com.example.listadecompras.domain.model.ShoppingList;
import com.example.listadecompras.main.AddNewShoppingList;
import com.example.listadecompras.util.RecyclerViewOnClickListenerHack;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private ShoppingListHelper shoppingListHelper;
    private ProductsHelper productsHelper;

    private ShoppingListAdapter shoppingListAdapter;

    private RecyclerView mRecyclerView;
    private List<ShoppingList> shoppingLists = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingListHelper = new ShoppingListHelper(this);

        productsHelper = new ProductsHelper(this);

        shoppingLists.addAll(shoppingListHelper.getAllShoppingLists());
        products.addAll(productsHelper.getAllProducts());
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_shoppingList);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setHasFixedSize(true);

        shoppingListAdapter = new ShoppingListAdapter(shoppingLists);
        shoppingListAdapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(shoppingListAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNewFragment();
            }
        });
    }

    @Override
    public void onClickListener(View view, int position) {
        showActionsDialog(shoppingLists.get(position), position);
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
    }

    private void showActionsDialog(final ShoppingList shoppingList, final int position) {
        CharSequence colors[] = new CharSequence[]{"Editar", "Deletar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma opção");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int acao) {
                if (acao == 0) {
                    showShoppingListDialog(true, shoppingList, position);
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
        shoppingListAdapter.notifyDataSetChanged();
    }

    private void showAddNewFragment() {
        Fragment newShoppingListFragment = new AddNewShoppingList(products);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.MainActivity, newShoppingListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showShoppingListDialog(final boolean shouldUpdate, final ShoppingList shoppingList, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.lembrete_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputNote = view.findViewById(R.id.note);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.label_novo_lembrete) : getString(R.string.label_editar_lembrete));

        if (shouldUpdate && shoppingList != null) {
            inputNote.setText(shoppingList.getName());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "Editar" : "Salvar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputNote.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Digite um lembrete!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                //if (shouldUpdate && shoppingList != null) {
                //    updateShoppingList(inputNote.getText().toString(), position);
                //} else {
                //    insertShoppingList(inputNote.getText().toString());
                //}
            }
        });
    }


}