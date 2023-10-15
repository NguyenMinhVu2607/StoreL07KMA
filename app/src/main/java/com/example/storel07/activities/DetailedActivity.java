package com.example.storel07.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.storel07.Model.RecommededModel;
import com.example.storel07.Model.ViewAllModel;
import com.example.storel07.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity  extends AppCompatActivity {
    TextView quantity;
    int totalquantity = 1;
    int totalPrice = 0;
    ImageView detailedImg,back_activity_detail;
    TextView price,rating,description;
    Button addtoCard;
    ImageView addItem,removeItem,back_to_activity_detail;
    ViewAllModel viewAllModel = null;
    RecommededModel recommededModel = null;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalied);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");

        if (object instanceof ViewAllModel) {
            viewAllModel = (ViewAllModel) object;
        }
        if (object instanceof RecommededModel) {
            recommededModel = (RecommededModel) object;
        }
        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detailed_img);
        back_activity_detail = findViewById(R.id.back_activity_detail);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_des);
        addtoCard = findViewById(R.id.add_to_card);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);
        back_to_activity_detail = findViewById(R.id.back_activity_detail);
        back_activity_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (viewAllModel != null) {

            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText(" " + viewAllModel.getPrice() + " VND");
        }
        if (recommededModel != null) {

            Glide.with(getApplicationContext()).load(recommededModel.getImg_url()).into(detailedImg);
            rating.setText(recommededModel.getRating());
            description.setText(recommededModel.getDescription());
            price.setText(" " + recommededModel.getPrice() + " VND");
        }
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (object instanceof ViewAllModel) {
                    ViewAllModel viewAllModel = (ViewAllModel) object;
                    if (totalquantity < 10) {
                        totalquantity++;
                        quantity.setText(String.valueOf(totalquantity));
                        totalPrice = viewAllModel.getPrice() * totalquantity;
                    }
                } else if (object instanceof RecommededModel) {
                    RecommededModel recommededModel = (RecommededModel) object;
                    if (totalquantity < 10) {
                        totalquantity++;
                        quantity.setText(String.valueOf(totalquantity));
                        totalPrice = recommededModel.getPrice() * totalquantity;
                    }
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (object instanceof ViewAllModel) {
                    ViewAllModel viewAllModel = (ViewAllModel) object;
                    if (totalquantity > 0) {
                        totalquantity--;
                        quantity.setText(String.valueOf(totalquantity));
                        totalPrice = viewAllModel.getPrice() * totalquantity;
                    }
                } else if (object instanceof RecommededModel) {
                    RecommededModel recommededModel = (RecommededModel) object;
                    if (totalquantity > 0) {
                        totalquantity--;
                        quantity.setText(String.valueOf(totalquantity));
                        totalPrice = recommededModel.getPrice() * totalquantity;
                    }
                }
            }
        });

        addtoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (object instanceof ViewAllModel) {
                    ViewAllModel viewAllModel = (ViewAllModel) object;
                    addedToCard(viewAllModel);
                } else if (object instanceof RecommededModel) {
                    RecommededModel recommededModel = (RecommededModel) object;
                    addedToCard(recommededModel);
                }
            }
        });
    }
    public void addedToCard(ViewAllModel viewAllModel) {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", viewAllModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice", viewAllModel.getPrice() * totalquantity);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    public void addedToCard(RecommededModel  recommededModel) {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", recommededModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice", recommededModel.getPrice() * totalquantity);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Added to Card", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
