package com.example.crimereporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
String username,password;
EditText txtUser,txtPas;
DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://crimereporting-9d082-default-rtdb.firebaseio.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUser =findViewById(R.id.username);
                txtPas = findViewById(R.id.password);
        findViewById(R.id.signup).setOnClickListener(view -> {startActivity(new Intent(this,SignUp.class));finishAfterTransition();});

        findViewById(R.id.login).setOnClickListener(view ->{
            username = txtUser.getText().toString().trim();
            password = txtPas.getText().toString().trim();
            if (username.isEmpty() | password.isEmpty()){
                Toast.makeText(this, "Ensure all fields are filled.", Toast.LENGTH_SHORT).show();
            }
            else {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                ref.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(username)){
                            String pass = snapshot.child(username).child("Password").getValue(String.class);
                            if(pass.equals(password)){
                                Toast.makeText(Login.this,"Login successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this,MainActivity.class));
                                finishAfterTransition();
                            }
                            else{
                                Toast.makeText(Login.this, "Wrong password Please try again", Toast.LENGTH_SHORT).show();
                                txtPas.setText("");
                            }

                        }
                        else{
                            Toast.makeText(Login.this, "The user does not exist in the database", Toast.LENGTH_SHORT).show();
                            txtUser.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Login.this, "Connection failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
        findViewById(R.id.exit_login).setOnClickListener(v ->{finishAndRemoveTask();});
    }
}