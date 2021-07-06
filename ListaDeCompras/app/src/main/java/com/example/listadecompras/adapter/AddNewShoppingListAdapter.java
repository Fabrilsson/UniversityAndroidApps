package com.example.listadecompras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.listadecompras.R;
import com.example.listadecompras.domain.model.Product;
import com.example.listadecompras.util.RecyclerViewOnClickListenerHack;

import java.util.List;

public class AddNewShoppingListAdapter extends RecyclerView.Adapter<AddNewShoppingListAdapter.ViewHolder> {

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    private List<Product> products;

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    public AddNewShoppingListAdapter(List<Product> list) {
        products = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView productName;
        public TextView productPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.product_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product item = products.get(position);
        holder.productName.setText(item.getName());
        holder.productPrice.setText(String.valueOf(item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public List<Product> getShoppingListProducts(){
        return products;
    }
}
