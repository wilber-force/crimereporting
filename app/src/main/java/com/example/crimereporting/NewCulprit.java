package com.example.crimereporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NewCulprit extends AppCompatActivity  {
EditText id,name,location;
RadioButton steal,rape,robery,overspeed,drunk;
String culidnumber,culname,cullocation;
RadioGroup select;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://crimereporting-9d082-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_culprit);
        setTitle("Enter a new Culprit details");
        id = findViewById(R.id.idNumberInput);
        name = findViewById(R.id.nameInput);
        location = findViewById(R.id.locationInput);
        select = findViewById(R.id.pink);

        steal = findViewById(R.id.stealing);
        rape = findViewById(R.id.rape);
        robery = findViewById(R.id.robery);
        overspeed = findViewById(R.id.overspeeding);
        drunk = findViewById(R.id.drunk);

        findViewById(R.id.insertBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                culidnumber = id.getText().toString();
                culname = name.getText().toString();
                cullocation = location.getText().toString();
                if (culidnumber.equalsIgnoreCase("") | culname.equalsIgnoreCase("") | cullocation.equalsIgnoreCase("") ) {
                    Toast.makeText(getApplicationContext(), "Make sure you have selected an item or filled the spaces.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int option =select.getCheckedRadioButtonId();
                    RadioButton sel = findViewById(option);
                    ref.child("Culprit").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(culidnumber)) {
                                String offence = snapshot.child(culidnumber).child("Crime committed").getValue(String.class);
                                Toast.makeText(NewCulprit.this, "The culprit already exists in our database for committing a "+offence+" offense", Toast.LENGTH_LONG).show();
                            } else {
                                ref.child("Culprit").child(culidnumber).child("Full name").setValue(culname);
                                ref.child("Culprit").child(culidnumber).child("Location").setValue(cullocation);
                                ref.child("Culprit").child(culidnumber).child("Crime committed").setValue(sel.getText());
                                Toast.makeText(NewCulprit.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                                Toast.makeText(NewCulprit.this, "You have entered: " + culidnumber + " of location " + cullocation + " and his/her name is " + culname+". The crime commited is "+sel.getText(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(NewCulprit.this, "Connection failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }


        });
        findViewById(R.id.cancelBtn2).setOnClickListener(view ->{finish();});

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