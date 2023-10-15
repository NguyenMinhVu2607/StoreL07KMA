package com.example.storel07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storel07.Model.MyOrderModel;
import com.example.storel07.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    Context context;
    List<MyOrderModel> myOrderModelList;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    int totalPrice = 0;
    public MyOrderAdapter(Context context, List<MyOrderModel> myOrderModelList) {
        this.context = context;
        this.myOrderModelList = myOrderModelList;
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order,parent,false));
    }




    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        holder.name.setText(myOrderModelList.get(position).getProductName());
        holder.quantity.setText(myOrderModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(myOrderModelList.get(position).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return myOrderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,quantity,totalPrice;
        public ViewHolder(View inflate) {
            super(inflate);
            name = itemView.findViewById(R.id.text_product_name);
            quantity = itemView.findViewById(R.id.text_product_sl);
            totalPrice = itemView.findViewById(R.id.text_product_price);
        }
        }
    }
