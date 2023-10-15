package com.example.storel07.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.storel07.Model.HomeCategory;
import com.example.storel07.Model.PopularModel;
import com.example.storel07.Model.RecommededModel;
import com.example.storel07.Model.UserModel;
import com.example.storel07.Model.ViewAllModel;
import com.example.storel07.R;
//import com.example.storel07.Utils.UtilsApp;
import com.example.storel07.Utils.UtilsApp;
import com.example.storel07.adapter.HomeAdapter;
import com.example.storel07.adapter.PopularAdapters;
import com.example.storel07.adapter.RecommendedAdapter;
import com.example.storel07.adapter.ViewAllAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private ImageView img_open_menu,profile_image;
    private NavigationView navigationView;
    TextView tv_profile,tv_category,tv_Mycart,tv_feedback,tv_share,tv_policy,tv_myorder;
    TextView nav_header_name, nav_header_mail;
    FirebaseFirestore db;
    FirebaseDatabase database;
    RecyclerView popularRec,homeCatRec,recommendedRec;
    PopularAdapters popularAdapters;
    List<PopularModel> popularModelList;
    List<HomeCategory> categoryList;
    List<RecommededModel> recommendedList;
    RecommendedAdapter recommendedAdapter;
    HomeAdapter homeAdapter;

    //Search Box
    EditText searchbox;
    RecyclerView recyclerViewSearch;
    List<ViewAllModel> viewAllModelList;
    ViewAllAdapter viewAllAdapter ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Navigation
        drawer = findViewById(R.id.drawer_layout);
        database = FirebaseDatabase.getInstance();
        navigationView = findViewById(R.id.navigationview);
        tv_profile = navigationView.getHeaderView(0).findViewById(R.id.tv_profile);
        profile_image = navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        nav_header_name = navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        nav_header_mail = navigationView.getHeaderView(0).findViewById(R.id.nav_header_mail);
        tv_Mycart = navigationView.getHeaderView(0).findViewById(R.id.tv_Mycart);
        tv_feedback = navigationView.getHeaderView(0).findViewById(R.id.tv_feedback);
        tv_share = navigationView.getHeaderView(0).findViewById(R.id.tv_share);
        tv_policy = navigationView.getHeaderView(0).findViewById(R.id.tv_policy);
        tv_category = navigationView.getHeaderView(0).findViewById(R.id.tv_category);
        tv_myorder = navigationView.getHeaderView(0).findViewById(R.id.tv_myorder);
        db = FirebaseFirestore.getInstance();

        //Searchbox
        recyclerViewSearch = findViewById(R.id.search_rec);
        searchbox = findViewById(R.id.search_box);
        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getApplicationContext(),viewAllModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }else {
                    searchProduct(editable.toString());
                }
            }
        });
        //Popular
        popularRec = findViewById(R.id.pop_rec);
        //Category
        recommendedRec =findViewById(R.id.recommended_rec);
        homeCatRec=findViewById(R.id.category_rec);
        database.getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        nav_header_name.setText(userModel.getName());
                        nav_header_mail.setText(userModel.getEmail());
                        Glide.with(getApplicationContext()).load(userModel.getProfileImg()).into(profile_image );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        tv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        tv_Mycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyCartsActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_view_profile.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsApp.INSTANCE.sendFeedback(MainActivity.this);            }
        });
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsApp.INSTANCE.share(MainActivity.this);            }
        });
        tv_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyOrder.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);        }
        });
        tv_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsApp.INSTANCE.openPrivacyPolicy(MainActivity.this);            }
        });
        popularRec.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(MainActivity.this,popularModelList);
        popularRec.setAdapter(popularAdapters);
        db.collection("PopularProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapters.notifyDataSetChanged();
//                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                            Toast.makeText(MainActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            //HomeCategory
        homeCatRec.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(MainActivity.this,categoryList);
        homeCatRec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
//                                Toast.makeText(MainActivity.this, "Success Cate", Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                            Toast.makeText(MainActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Recommeded
        recommendedRec.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false));
        recommendedList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(MainActivity.this,recommendedList);
        recommendedRec.setAdapter(recommendedAdapter);

        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommededModel recommededModel = document.toObject(RecommededModel.class);
                                recommendedList.add(recommededModel);
                                recommendedAdapter.notifyDataSetChanged();
//                                Toast.makeText(MainActivity.this, "Success Rec", Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                            Toast.makeText(MainActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        navigationView.getHeaderView(0).findViewById(R.id.textview_bmi)
        img_open_menu = findViewById(R.id.open_menu_home);
        img_open_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }
    private  void searchProduct(String type){
        if(!type.isEmpty()){
            db.collection("AllProduct").whereEqualTo("type",type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful() && task.getResult() != null){
                                viewAllModelList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for(DocumentSnapshot doc : task.getResult().getDocuments()){
                                    ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }

    }
}
