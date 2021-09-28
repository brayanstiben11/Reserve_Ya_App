package com.example.reserve_ya_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private  Button mbuttonsignOut ;
    FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mbuttonsignOut = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();


        mbuttonsignOut.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            finish();

        });
    }
}