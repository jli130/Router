package com.example.router_route_creation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String user = getIntent().getStringExtra("user");

        Log.e("MainActivity", "dataGet: " + user);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_NavigationView1); // get bottom nav bar
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        // main page layout
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("some_int1", 0);
            bundle.putString("user", user);
            Log.e("MainActivity", "dataPassed: " + user);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.framLayout, mainFragement.class, bundle)
                    .commit();
        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    String user = getIntent().getStringExtra("user");

                    switch (item.getItemId()){
                        case R.id.page_1:
                            // if clicked on saved routes button, it will bring user to saved routes activity
                            Intent intent1 = new Intent(MainActivity.this, SavedRoutes.class);
                            intent1.putExtra("user", user);
                            Log.e("MainActivity", "dataPassed: " + user);
                            startActivity(intent1);
                            break;
                        case R.id.page_2:
                            // if clicked on create routers button, it would switch back and forth between main page and
                            // and routes creation page
                            Intent intent2 = new Intent(MainActivity.this, route_creation.class);
                            intent2.putExtra("user", user);
                            Log.e("MainActivity", "dataPassed: " + user);
                            startActivity(intent2);
                            break;
                        case R.id.page_3:
                            // if clicked on user profile button, it will bring user to user profile activity
                            Intent intent3 = new Intent(MainActivity.this, UserProfile.class);

                            intent3.putExtra("user", user);
                            Log.e("MainActivity", "dataPassed: " + user);
                            startActivity(intent3);
                            break;
                        case R.id.page_4:
                            Intent intent4 = new Intent(MainActivity.this, PreviousRoutes.class);
                            intent4.putExtra("user", user);
                            Log.e("MainActivity", "dataPassed: " + user);
                            startActivity(intent4);
                    }
                return true;
                }
    };

}