package com.example.storel07.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storel07.Model.NavcategoryModel;
import com.example.storel07.Model.PopularModel;
import com.example.storel07.R;
import com.example.storel07.adapter.NavCategoryAdapter;
import com.example.storel07.adapter.PopularAdapters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private ImageView img_open_menu;
    private NavigationView navigationView;
    List<NavcategoryModel> categoryModelList;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    TextView tv_home,tv_profile,tv_category;
    NavCategoryAdapter navCategoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        db = FirebaseFirestore.getInstance();
        drawer = findViewById(R.id.drawer_layout_cat);
        //navi
        navigationView = findViewById(R.id.navigationview);

        tv_profile = navigationView.getHeaderView(0).findViewById(R.id.tv_profile);
        tv_category = navigationView.getHeaderView(0).findViewById(R.id.tv_category);
        tv_home = navigationView.getHeaderView(0).findViewById(R.id.tv_home);
        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        //
        recyclerView = findViewById(R.id.cat_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
        categoryModelList = new ArrayList<>();
        navCategoryAdapter = new NavCategoryAdapter(CategoryActivity.this,categoryModelList);
        recyclerView.setAdapter(navCategoryAdapter);
        db.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NavcategoryModel navcategoryModel = document.toObject(NavcategoryModel.class);
                                categoryModelList.add(navcategoryModel);
                                navCategoryAdapter.notifyDataSetChanged();
                                Toast.makeText(CategoryActivity.this, "Success NAV CAT", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CategoryActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        img_open_menu = findViewById(R.id.open_menu_home_cat);
        img_open_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }
}