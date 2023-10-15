package com.example.storel07.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storel07.Model.ViewAllModel;
import com.example.storel07.R;
import com.example.storel07.adapter.ViewAllAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    ImageView imageView;
    List<ViewAllModel> viewAllModelsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        firestore = FirebaseFirestore.getInstance();
        imageView = findViewById(R.id.back_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewAllModelsList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, viewAllModelsList);
        recyclerView.setAdapter(viewAllAdapter);
        //Get Book
        if(type != null && type.equalsIgnoreCase("book")){
            firestore.collection("AllProduct").whereEqualTo("type","book").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelsList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
        //Get Toy
        if(type != null && type.equalsIgnoreCase("toy")){
            firestore.collection("AllProduct").whereEqualTo("type","toy").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelsList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
        //Get Tech
        if(type != null && type.equalsIgnoreCase("technology")){
            firestore.collection("AllProduct").whereEqualTo("type","technology").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelsList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
        //Get Stationery
        if(type != null && type.equalsIgnoreCase("stationery")){
            firestore.collection("AllProduct").whereEqualTo("type","stationery").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelsList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
        //Get Drawing
        if(type != null && type.equalsIgnoreCase("drawing")){
            firestore.collection("AllProduct").whereEqualTo("type","drawing").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelsList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
    }
}