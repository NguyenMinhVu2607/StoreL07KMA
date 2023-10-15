package com.example.storel07.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.storel07.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth auth;
    AppCompatButton layoutcon_signUP,layoutcon_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_activity);
        layoutcon_signUP =findViewById(R.id.layoutcon_signUP);
        layoutcon_login =findViewById(R.id.layoutcon_login);
        auth = FirebaseAuth.getInstance();
//        progressBar = findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.GONE);
        if (auth.getCurrentUser() != null){
//            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            Toast.makeText(this, "please wait you are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }
        layoutcon_signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration(view);
            }
        });
        layoutcon_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login(view);

            }
        });
    }

    public void login(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    public void registration(View view) {

        startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
    }

}