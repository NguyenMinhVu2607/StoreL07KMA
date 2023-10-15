package com.example.storel07.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.storel07.Model.MyCartModel;
import com.example.storel07.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class PleacedOrderActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);
        auth= FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        List<MyCartModel> list = (List<MyCartModel>) getIntent().getSerializableExtra("itemList");
        if(list != null && list.size() >0 ){
            for(MyCartModel model : list){
                final HashMap<String,Object> cartMap = new HashMap<>();
                cartMap.put("productName",model.getProductName());
                cartMap.put("productPrice",model.getProductPrice());
                cartMap.put("currentDate",model.getCurrentDate());
                cartMap.put("currentTime",model.getCurrentTime());
                cartMap.put("totalQuantity",model.getTotalQuantity());
                cartMap.put("totalPrice",model.getTotalPrice());
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(PleacedOrderActivity.this, "Your Order has beeen success", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }
}
