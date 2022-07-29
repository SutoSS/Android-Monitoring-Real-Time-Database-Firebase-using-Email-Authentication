package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView x, y, z, batt, heartrate,spo2;
    LinearLayout greetImg;
    TextView greetText;
    Button btnLogOut;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Query myRef;
    private DatabaseReference rootDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greetImg = findViewById(R.id.greeting_img);
        greetText = findViewById(R.id.greeting_text);
        x = findViewById(R.id.tv_x);
        y = findViewById(R.id.tv_y);
        z = findViewById(R.id.tv_z);
        heartrate = findViewById(R.id.tv_heartrate);
        batt = findViewById(R.id.tv_batt);
        spo2 = findViewById(R.id.tv_spo2);
        greeting();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("animalmon");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nilai1 = snapshot.child("x").getValue().toString();
                Log.d(TAG, "Value is: " + nilai1);
                String nilai2 = snapshot.child("y").getValue().toString();
                Log.d(TAG, "Value is: " + nilai2);
                String nilai3 = snapshot.child("z").getValue().toString();
                Log.d(TAG, "Value is: " + nilai3);
                String nilai4 = snapshot.child("batt").getValue().toString();
                Log.d(TAG, "Value is: " + nilai4);
                String nilai5 = snapshot.child("heart_rate").getValue().toString();
                Log.d(TAG, "Value is: " + nilai5);
                String nilai6 = snapshot.child("SpO2").getValue().toString();
                Log.d(TAG, "Value is: " + nilai6);

                x.setText(nilai1);
                y.setText(nilai2);
                z.setText(nilai3);
                batt.setText(nilai4);
                heartrate.setText(nilai5);
                spo2.setText(nilai6);

        }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btnLogOut = findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

    }

    @SuppressLint("SetTextI18n")
    private void greeting() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12){
            greetText.setText("Selamat Pagi");
            greetImg.setBackgroundResource(R.drawable.img_default_half_morning);
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            greetText.setText("Selamat Siang");
            greetImg.setBackgroundResource(R.drawable.img_default_half_afternoon);
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            greetText.setText("Selamat Sore");
            greetImg.setBackgroundResource(R.drawable.img_default_half_without_sun);
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            greetText.setText("Selamat Malam");
            greetText.setTextColor(Color.WHITE);
            greetImg.setBackgroundResource(R.drawable.img_default_half_night);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}