package com.example.router_route_creation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.router_route_creation.databinding.PreviousFragmentBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class PreviousRoutes extends AppCompatActivity{
    PreviousRouteDatabaseHelper db;
    PreviousRoutesAdapter adapter;
    PreviousFragmentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PreviousFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new PreviousRouteDatabaseHelper(this);
  
        ArrayList<Routes> routes = db.getData();
        String addUser = getIntent().getStringExtra("user");
        String addType = getIntent().getStringExtra("type");
        String addStart = getIntent().getStringExtra("start");
        String addEnd = getIntent().getStringExtra("end");
        String addTime = getIntent().getStringExtra("time");
        if((addType != null) && (addUser != null)){
            if(ifInsert(routes, addUser, addType, addStart, addEnd, addTime)) {
                db.addContent(addUser, addType, addStart, addEnd, addTime);
            }
            else{
                Toast.makeText(PreviousRoutes.this, "You have saved this route already", Toast.LENGTH_LONG).show();
            }

        }
        ArrayList<Routes> finished = db.getData();
        ArrayList<Routes> customList = new ArrayList<>();
        for (Routes route : finished) {
            if(route.getUser().equals(addUser)){
                customList.add(route);
            }
        }

        if(customList.size() > 0) {
            adapter = new PreviousRoutesAdapter(customList, this);
            binding.previousView.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            binding.previousView.setLayoutManager(linearLayoutManager);
            binding.previousView.getAdapter().notifyDataSetChanged();
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    String user = getIntent().getStringExtra("user");
                    switch (item.getItemId()) {
                        case R.id.page_1:
                            Intent intent1 = new Intent(PreviousRoutes.this, SavedRoutes.class);
                            intent1.putExtra("user", user);
                            Log.e("PreviousRoutes", "dataPassed: " + user);
                            startActivity(intent1);
                            break;

                        case R.id.page_2:
                            // if clicked on create routers button, it would switch back and forth between main page and
                            // and routes creation page
                            Intent intent2 = new Intent(PreviousRoutes.this, MainActivity.class);
                            intent2.putExtra("user", user);
                            Log.e("PreviousRoutes", "dataPassed: " + user);
                            startActivity(intent2);
                            break;
                        case R.id.page_3:
                            // if clicked on create routers button, it would switch back and forth between main page and
                            // and routes creation page
                            Intent intent3 = new Intent(PreviousRoutes.this, UserProfile.class);
                            intent3.putExtra("user", user);
                            Log.e("PreviousRoutes", "dataPassed: " + user);
                            startActivity(intent3);
                            break;
                        case R.id.page_4:
                            break;
                    }
                    return true;
                }
            };

    private boolean ifInsert(ArrayList<Routes> routeList,String user, String type, String start, String end, String time){
        for (Routes routes : routeList) {
            if(routes.getType()!= null) {
                if ((routes.getUser().equals(user)) && (routes.getType().equals(type)) && (routes.getStart().equals(start))
                        && (routes.getEnd().equals(end)) && (routes.getTime().equals(time))) {
                    return false;
                }
            }
        }
        return true;
    }

}
