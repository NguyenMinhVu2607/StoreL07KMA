package com.example.storel07.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storel07.Model.NavCategoryDetailedModel;
import com.example.storel07.Model.NavcategoryModel;
import com.example.storel07.Model.ViewAllModel;
import com.example.storel07.R;
import com.example.storel07.adapter.NavCategoryDetailedAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView back_home_cate;
    List<NavCategoryDetailedModel> list;
    NavCategoryDetailedAdapter adapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);
        recyclerView = findViewById(R.id.nav_cat_det_rec);
        back_home_cate = findViewById(R.id.back_home_navcate);
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        String type = getIntent().getStringExtra("type");
        back_home_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NavCategoryDetailedAdapter(this, list);
        recyclerView.setAdapter(adapter);


        if(type != null && type.equalsIgnoreCase("book")){
            db.collection("NavCategoryDetailed").whereEqualTo("type","book").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryDetailedModel viewAllModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(viewAllModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        }
        if(type != null && type.equalsIgnoreCase("technology")){
            db.collection("NavCategoryDetailed").whereEqualTo("type","technology").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryDetailedModel viewAllModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(viewAllModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        }
    }

}
