package com.example.router_route_creation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

// the activity that creates the user profile
public class UserProfile extends AppCompatActivity {
    CommentDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        db = new CommentDatabaseHelper(this);
        String addType = getIntent().getStringExtra("type");
        String addStart = getIntent().getStringExtra("start");
        String addEnd = getIntent().getStringExtra("end");
        String addTime = getIntent().getStringExtra("time");
        String addComment = getIntent().getStringExtra("comment");
        String addUser = getIntent().getStringExtra("user");
        Log.e("UserProfile", "dataGet: " + addUser);
        ArrayList<Comments> comments = db.getData();
        if((addComment!=null) && (addUser != null)) {
            if (ifInsert(comments, addUser, addType, addStart, addEnd, addTime)) {
                db.addContent(addUser, addType, addStart, addEnd, addTime, addComment);
            }
            else{
                db.update(addUser, addType, addStart, addEnd, addTime, addComment);
            }
        }
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("some_int1", 0);
            bundle.putString("user", addUser);
            Log.e("UserProfile", "dataPassed: " + addUser);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.userprofile, FirstFragment.class, bundle)
                    .commit();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_NavigationView1);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        Button button1 = (Button) findViewById(R.id.bAccount);//get id of button 1
        Button button2 = (Button) findViewById(R.id.bProutes);//get id of button 1
        Button button3 = (Button) findViewById(R.id.bSroutes);//get id of button 1
        Button button4 = (Button) findViewById(R.id.bComments);//get id of button 1
        Button button5 = (Button) findViewById(R.id.bLogOut);//get id of button 1

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if clicked button 1, it brings to the account information page displayed in the
                // frame layout
                Bundle bundle = new Bundle();
                bundle.putInt("some_int1", 0);
                bundle.putString("user", addUser);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.userprofile, FirstFragment.class, bundle)
                        .commit();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if clicked button 2, it brings to the saved routes page displayed in the
                // frame layout
                Bundle bundle = new Bundle();
                bundle.putInt("some_int2", 1);
                bundle.putString("user", addUser);
                Log.e("UserProfile", "dataPassed: " + addUser);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.userprofile, ThirdFragment.class, bundle)
                        .commit();

            }

        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if clicked button 3, it brings to the previous routes page displayed in the
                // frame layout
                Bundle bundle = new Bundle();
                bundle.putInt("some_int3", 2);
                bundle.putString("user", addUser);
                Log.e("UserProfile", "dataPassed: " + addUser);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.userprofile, SecondFragment.class, bundle)
                        .commit();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if clicked button 4, it brings to the comment page displayed in the
                // frame layout
                Bundle bundle = new Bundle();
                bundle.putInt("some_int4", 3);
                bundle.putString("user", addUser);
                Log.e("UserProfile", "dataPassed: " + addUser);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.userprofile, FourthFragment.class, bundle)
                        .commit();


            }

        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if clicked button 5,  it will log out from the current activity and brings the
                // user back to log in page.
                Intent intent = new Intent(UserProfile.this, LogIn.class);
                startActivity(intent);
            }

        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    String user = getIntent().getStringExtra("user");
                    String pass = getIntent().getStringExtra("password");
                    switch (item.getItemId()){
                        case R.id.page_1:
                            Intent intent1 = new Intent(UserProfile.this, SavedRoutes.class);
                            intent1.putExtra("user", user);
                            intent1.putExtra("password", pass);
                            Log.e("UserProfile", "dataPassed: " + user);
                            startActivity(intent1);
                            break;
                        case R.id.page_2:
                            Intent intent2 = new Intent(UserProfile.this, MainActivity.class);
                            intent2.putExtra("user", user);
                            Log.e("UserProfile", "dataPassed: " + user);
                            startActivity(intent2);
                            break;
                        case R.id.page_3:
                            break;
                        case R.id.page_4:
                            Intent intent3 = new Intent(UserProfile.this, PreviousRoutes.class);
                            intent3.putExtra("user", user);
                            Log.e("UserProfile", "dataPassed: " + user);
                            startActivity(intent3);
                    }
                    return true;
                }
            };
    private boolean ifInsert(ArrayList<Comments> commentList, String user, String type, String start, String end, String time){
        for (Comments comment : commentList) {
            if(comment.getType()!= null) {
                if ((comment.getUser().equals(user)) && (comment.getType().equals(type)) && (comment.getStart().equals(start))
                        && (comment.getEnd().equals(end)) && (comment.getTime().equals(time))) {
                    return false;
                }
            }
        }
        return true;
    }
}