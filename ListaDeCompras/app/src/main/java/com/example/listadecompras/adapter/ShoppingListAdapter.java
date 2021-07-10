package com.example.listadecompras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.listadecompras.R;

import com.example.listadecompras.domain.model.ShoppingList;
import com.example.listadecompras.util.RecyclerViewOnClickListenerHack;
import com.example.listadecompras.util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    private List<ShoppingList> list;

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    public ShoppingListAdapter(List<ShoppingList> shoppingLists) {
        list = shoppingLists;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewLembrete;
        public TextView textViewData;
        public TextView textViewId;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewLembrete = (TextView) itemView.findViewById(R.id.textViewLembrete);
            textViewData = (TextView) itemView.findViewById(R.id.textViewData);
            textViewId = (TextView) itemView.findViewById(R.id.textViewId);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(view, getPosition());
            }
        }
    }

    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.lembrete_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShoppingList item = list.get(position);
        holder.textViewLembrete.setText(item.getName());
        holder.textViewData.setText(Util.format(item.getDataHora(), "dd/MM/yyyy"));
        holder.textViewId.setText(String.valueOf(item.getId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addShoppingList(ShoppingList shoppingList){

        if(list == null)
            list = new ArrayList<>();

        list.add(shoppingList);

        list.sort(Comparator.comparing(ShoppingList::getId, Comparator.reverseOrder()));
    }

    public void updateShoppingList(ShoppingList shoppingList){

        list.removeIf(s -> s.getId() == shoppingList.getId());

        list.add(shoppingList);

        list.sort(Comparator.comparing(ShoppingList::getId, Comparator.reverseOrder()));
    }
}
