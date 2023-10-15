package com.example.storel07.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storel07.Model.InfoUser;
import com.example.storel07.Model.MyCartModel;
import com.example.storel07.Model.MyOrderModel;
import com.example.storel07.Model.UserModel;
import com.example.storel07.R;
import com.example.storel07.activities.Fragment.BottomSheetFragment;
import com.example.storel07.adapter.MyCartAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartsActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalAmount,phoneNumber,Address,nameUSer;
    RecyclerView recyclerView;
    Button buy_now;
    ImageView back_activity_mycart;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    String phone,address, name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycarts);
        overTotalAmount = findViewById(R.id.total_Amount);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerview);
        Address = findViewById(R.id.Address);
        phoneNumber = findViewById(R.id.phoneNumber);
        nameUSer = findViewById(R.id.nameUSer);
        buy_now = findViewById(R.id.buy_now);
        back_activity_mycart = findViewById(R.id.back_activity_mycart);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyCartsActivity.this));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(MyCartsActivity.this,cartModelList);
        recyclerView.setAdapter(cartAdapter);

        back_activity_mycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        LocalBroadcastManager.getInstance(MyCartsActivity.this)
//                        .registerReceiver(broadcastReceiver,new IntentFilter("MyTotalAmount"));
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String documentId = documentSnapshot.getId();

                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                                cartModel.setDocumentId(documentId);
                                cartModelList.add(cartModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                            calculateTotalAmount(cartModelList);
                        }
                    }
                });
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("UserInfo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String documentId = documentSnapshot.getId();
                                InfoUser infoUser = documentSnapshot.toObject(InfoUser.class);
                                if (infoUser!=null) {
                                    Log.d("UserInfo",""+infoUser.getNumber());
                                    phoneNumber.setText(infoUser.getNumber());
                                    Address.setText(infoUser.getAddress());
                                    nameUSer.setText(infoUser.getName());
                                     phone = infoUser.getNumber().toString();
                                     address = infoUser.getAddress().toString();
                                     name = infoUser.getName().toString();
                                } else {
                                    Toast.makeText(MyCartsActivity.this, "Vui lòng sang mục Profile để cập nhật thông tin giao hàng !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("phone",""+phone);
                String test = "";
                if (phone !=test && address !=test && name !=test) {
                    Intent intent = new Intent(MyCartsActivity.this, PleacedOrderActivity.class);
                    intent.putExtra("itemList", (Serializable) cartModelList);
                    startActivity(intent);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(view, "Vui lòng nhập đầy đủ thông tin của bạn !", Snackbar.LENGTH_LONG)
                            .setAction("Nhập thông tin", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MyCartsActivity.this, ProfileActivity.class);
                                    startActivity(intent);                                }
                            });
                    snackbar.show();
//                    Intent intent = new Intent(MyCartsActivity.this, ProfileActivity.class);
//                    startActivity(intent);
//                    Toast.makeText(MyCartsActivity.this, "Vui lòng nhập đầy đủ thông tin để có thể đặt hàng !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
//        public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                int totalBill = intent.getIntExtra("totalAmount",0);
//                overTotalAmount.setText("Total Bill : "+totalBill+ "vnd");
//            }
//        };
        private void calculateTotalAmount(List<MyCartModel> cartModelList) {
                double totalAmount = 0.0;
                for (MyCartModel myCartModel : cartModelList){
                    totalAmount += myCartModel.getTotalPrice();
//                    Toast.makeText(this, "ok :" +totalAmount, Toast.LENGTH_SHORT).show();
                }
                overTotalAmount.setText("" + totalAmount);
        }
}
