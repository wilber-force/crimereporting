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

public class SignUp extends AppCompatActivity {
String fname,surname,idno,phone,password1,password2;
EditText first,sur,id,phoneno,pass1,pass2;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://crimereporting-9d082-default-rtdb.firebaseio.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        first = findViewById(R.id.first);
        sur = findViewById(R.id.surname);
        id = findViewById(R.id.idNumberInput);
        phoneno = findViewById(R.id.phone);
        pass1 = findViewById(R.id.password1);
        pass2 = findViewById(R.id.password2);

        findViewById(R.id.loginRefer).setOnClickListener(v->{startActivity(new Intent(SignUp.this,Login.class));finishAfterTransition();});


        findViewById(R.id.signUpbtn).setOnClickListener(v->{
            fname = first.getText().toString().trim();
            surname = sur.getText().toString().trim();
            idno = id.getText().toString().trim();
            phone = phoneno.getText().toString().trim();
            password1 = pass1.getText().toString().trim();
            password2 = pass2.getText().toString().trim();
            if (fname.isEmpty() | surname.isEmpty() | idno.isEmpty() | phone.isEmpty() | password1.isEmpty()){
                Toast.makeText(SignUp.this, "Ensure that all fields are filled except for the middle name if you lack one.", Toast.LENGTH_SHORT).show();
            }
            else if (password1.equals(password2)){
                ref.child("Users").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(idno)) {
                            Toast.makeText(SignUp.this, "The id number already exists in our database", Toast.LENGTH_LONG).show();
                        } else {
                            ref.child("Users").child(idno).child("First name").setValue(fname);
                            ref.child("Users").child(idno).child("Surname").setValue(surname);
                            ref.child("Users").child(idno).child("Phone Number").setValue(phone);
                            ref.child("Users").child(idno).child("Password").setValue(password1);
                            Toast.makeText(SignUp.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, Login.class));
                            finishAfterTransition();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SignUp.this, "Connection failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                Toast.makeText(SignUp.this, "The passwords did not match", Toast.LENGTH_SHORT).show();
                pass1.setText("");pass2.setText("");
            }
        });
    }
}