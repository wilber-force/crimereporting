package com.example.crimereporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.newRecord).setOnClickListener(View ->{startActivity(new Intent(MainActivity.this,NewCulprit.class));});
        findViewById(R.id.search).setOnClickListener(View ->{startActivity(new Intent(MainActivity.this,SearchCulprit.class));});
        findViewById(R.id.update).setOnClickListener(View ->{startActivity(new Intent(MainActivity.this,UpdateDetails.class));});

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