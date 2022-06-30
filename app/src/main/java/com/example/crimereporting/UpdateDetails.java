package com.example.crimereporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateDetails extends AppCompatActivity {
EditText id,idno,name,location,crime;
String idNumber;
TextView text;
Button update,cancel;
LinearLayout main;
DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://crimereporting-9d082-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_details);
        id = findViewById(R.id.update_id);
        update = findViewById(R.id.updateBtn);
        cancel = findViewById(R.id.cancelBtn);
        main=findViewById(R.id.listShow);
        idno = findViewById(R.id.idNumberoutput);
        name = findViewById(R.id.nameoutput);
        location = findViewById(R.id.locationoutput);
        crime = findViewById(R.id.crimeoutput);

        findViewById(R.id.exitBtn).setOnClickListener(view ->{finish();});
        cancel.setOnClickListener(view -> finish());
        findViewById(R.id.searchBtn).setOnClickListener(view -> {
            idNumber = id.getText().toString().trim();
            if (idNumber.isEmpty()){
                Toast.makeText(UpdateDetails.this, "Ensure all fields are filled.", Toast.LENGTH_SHORT).show();
            }
            else {
                ref.child("Culprit").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(idNumber)){
                            String culLocation = snapshot.child(idNumber).child("Location").getValue(String.class);
                            String culname = snapshot.child(idNumber).child("Full name").getValue(String.class);
                            String culcrime = snapshot.child(idNumber).child("Crime committed").getValue(String.class);

                            idno.setText(idNumber);
                            location.setText(culLocation);
                            name.setText(culname);
                            crime.setText(culcrime);
                            main.setVisibility(View.VISIBLE);

                            update.setOnClickListener(View ->{
                                String updateId = idno.getText().toString().trim();
                                String updatelocation = location.getText().toString().trim();
                                String updateName = name.getText().toString().trim();
                                String updateCrime = crime.getText().toString().trim();

                                ref.child("Culprit").child(updateId).child("Full name").setValue(updateName);
                                ref.child("Culprit").child(updateId).child("Location").setValue(updatelocation);
                                ref.child("Culprit").child(updateId).child("Crime committed").setValue(updateCrime);

                                Toast.makeText(UpdateDetails.this, "Details updated Successfully ", Toast.LENGTH_SHORT).show();

                            });
                        }
                        else{
                            Toast.makeText(UpdateDetails.this, "The Cuplrit does not exist in our system.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UpdateDetails.this, "Failed to connect", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAfterTransition();
                break;
            case R.id.logout:
                startActivity(new Intent(this, Login.class));
                finishAfterTransition();
                break;
            case R.id.exit_menu:
                finishAndRemoveTask();
                break;
            default:
                finish();

        }
        return super.onOptionsItemSelected(item);
    }
}