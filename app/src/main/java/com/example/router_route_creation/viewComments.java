package com.example.router_route_creation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.router_route_creation.databinding.CommentViewPageBinding;
import com.example.router_route_creation.databinding.SavedRoutesBinding;

import java.util.ArrayList;

public class viewComments extends AppCompatActivity {
    CommentDatabaseHelper db;
    CommentViewPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CommentViewPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button button = (Button) findViewById(R.id.back);
        db = new CommentDatabaseHelper(this);
        String user = getIntent().getStringExtra("user");
        Log.e("viewComments", "dataGet: " + user);

        ArrayList<Comments> routes = db.getData();
        ArrayList<Comments> customList = new ArrayList<>();
        for (Comments comment : routes) {
            if(comment.getUser().equals(user)){
                customList.add(comment);
            }
        }
        if(customList.size() > 0){
            RecyclerView recyclerView = findViewById(R.id.cRecyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            CommentAdapter commentAdapter = new CommentAdapter(customList, this);
            recyclerView.setAdapter(commentAdapter);
            commentAdapter.notifyDataSetChanged();
        }
        else{

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewComments.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });


    }
}
