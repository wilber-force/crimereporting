package com.example.crimereporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchCulprit extends AppCompatActivity {
EditText id;
String userid;
TextView text;
DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://crimereporting-9d082-default-rtdb.firebaseio.com");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_culprit);
        id = findViewById(R.id.Idsearch);
        text = findViewById(R.id.idscrollList);
        findViewById(R.id.idexitBtn).setOnClickListener(view ->{finish();});
        findViewById(R.id.idsearchBtn).setOnClickListener(view -> {
            userid = id.getText().toString().trim();
            if (userid.isEmpty()){
                Toast.makeText(SearchCulprit.this, "Please ensure you have filled the Id number", Toast.LENGTH_SHORT).show();
            }
            else {
                text.setVisibility(View.VISIBLE);
                Toast.makeText(SearchCulprit.this, "Please wait...", Toast.LENGTH_SHORT).show();
                ref.child("Culprit").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(userid)){
                            String uId=userid;
                            String name = snapshot.child(userid).child("Full name").getValue(String.class);
                            String location = snapshot.child(userid).child("Location").getValue(String.class);
                            String crime = snapshot.child(userid).child("Crime committed").getValue(String.class);
                            text.setText("Culprit id number: "+uId+"\nFull name: "+name+"\nLocation: "+location+"\nCrime committed: "+crime);
                        }
                        else{
                            text.setText("The culprit does not exist in our database.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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