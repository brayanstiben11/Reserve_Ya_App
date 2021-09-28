  package com.example.reserve_ya_app;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

  public class MainActivity extends AppCompatActivity {


    private  EditText mEditTextnombre ;
    private  EditText mEditTextEmail ;
    private  Button mButtonRegister ;
    private  EditText mEditTextPassword ;
    private  Button mButtonlogin ;
    private String nombre = "";
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth ;
    DatabaseReference  mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextnombre = findViewById(R.id.editTextNombre);
        mEditTextEmail = findViewById(R.id.editTextemail);
        mEditTextPassword = findViewById(R.id.editTextPassword);
        mButtonRegister = findViewById(R.id.signup);
        mButtonlogin = findViewById(R.id.gologin);

        mAuth =  FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mButtonRegister.setOnClickListener(view -> {

            nombre= mEditTextnombre.getText().toString();
            email = mEditTextEmail.getText().toString();
            password = mEditTextPassword.getText().toString();

            if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty())
            {
                if (password.length() >=6)
                {
                    registerUser();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "La conttraseña debe tener al menos 6 caracteres",Toast.LENGTH_LONG).show();
                }

            }

            else
            {
                Toast.makeText(MainActivity.this, "Debe completar los campos", Toast.LENGTH_LONG).show();
            }

        });
        mButtonlogin.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,LoginActivity.class)));
    }

    private void registerUser ()
    {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                Map <String, Object > map = new HashMap<>();
                map.put("name", nombre);
                map.put("email",email);
                map.put("password",password);


                String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(task2 -> {
                if(task2.isSuccessful())
                {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "El registro no se pudo hacerse correctamente", Toast.LENGTH_LONG).show();
                }
            });
            }
            else
            {
                Toast.makeText(MainActivity.this, "No se pudo crear el usuario satisfactoriamente", Toast.LENGTH_LONG).show();
            }
        });
    }

      @Override
      protected void onStart() {
          super.onStart();

          if (mAuth.getCurrentUser()!=null)
          {
              startActivity(new Intent (MainActivity.this,ProfileActivity.class));
              finish();
          }
      }
  }