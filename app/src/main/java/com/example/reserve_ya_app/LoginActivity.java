package com.example.reserve_ya_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditTextEmail ;
    private  EditText mEditTextPassword ;
    private  Button mButtonLogin ;
    FirebaseAuth mAuth ;

    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextEmail = findViewById(R.id.editTextemail);
        mEditTextPassword = findViewById(R.id.editTextPassword);
        mButtonLogin = findViewById(R.id.login);

        mButtonLogin.setOnClickListener(view -> {
            email = mEditTextEmail.getText().toString();
            password = mEditTextPassword.getText().toString();

            if (!email.isEmpty() && !password.isEmpty())
            {
                loginUser();
            }
            else
            {
                Toast.makeText(LoginActivity.this,"Complete los campos",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginUser()
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if  (task.isSuccessful())
            {
                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
            }
            else
            {
                Toast.makeText(LoginActivity.this, "No se pudo iniciar sesion satisfactoriamente",Toast.LENGTH_LONG).show();
            }
        });
    }
}