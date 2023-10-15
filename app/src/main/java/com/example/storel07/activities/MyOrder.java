package com.example.storel07.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storel07.Model.MyCartModel;
import com.example.storel07.Model.MyOrderModel;
import com.example.storel07.R;
import com.example.storel07.adapter.MyCartAdapter;
import com.example.storel07.adapter.MyOrderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyOrder extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    ImageView back_home_order;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    MyOrderAdapter myOrderAdapter;
    List<MyOrderModel> orderModelList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        recyclerView = findViewById(R.id.my_order);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        back_home_order = findViewById(R.id.back_home_order);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(MyOrder.this));
        orderModelList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(MyOrder.this, orderModelList);
        recyclerView.setAdapter(myOrderAdapter);
        back_home_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String documentId = documentSnapshot.getId();

                                MyOrderModel myOrderModel = documentSnapshot.toObject(MyOrderModel.class);
                                myOrderModel.setDocumentId(documentId);
                                orderModelList.add(myOrderModel);
                                myOrderAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}
