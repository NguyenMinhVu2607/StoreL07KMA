package com.example.storel07.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.storel07.Model.UserModel;
import com.example.storel07.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity_view_profile extends AppCompatActivity {
    TextView tv_name,tv_mail;
    FirebaseDatabase database;
    ImageView profile_img_view;
    AppCompatButton my_setting_pro,my_order_pro,my_cart_pro,logout_pro;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        tv_name =findViewById(R.id.tv_name);
        tv_mail =findViewById(R.id.tv_mail);
        my_setting_pro =findViewById(R.id.my_setting_pro);
        my_order_pro =findViewById(R.id.my_order_pro);
        my_cart_pro =findViewById(R.id.my_cart_pro);
        logout_pro =findViewById(R.id.logout_pro);
        database = FirebaseDatabase.getInstance();
        profile_img_view =findViewById(R.id.profile_img_view);
        database.getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        tv_name.setText(userModel.getName());
                        tv_mail.setText(userModel.getEmail());
                        Glide.with(getApplicationContext()).load(userModel.getProfileImg()).into(profile_img_view  );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        my_setting_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        my_order_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyOrder.class);
                startActivity(intent);
                finish();
            }
        });
        my_cart_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyCartsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        logout_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                navigateToLoginScreen();
            }
        });

    }
    private void navigateToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Kết thúc màn hình hiện tại sau khi chuyển đến màn hình đăng nhập
    }
}