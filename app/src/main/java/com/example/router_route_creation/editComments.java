package com.example.router_route_creation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class editComments extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_comments);
        EditText input = findViewById(R.id.editText);
        String addUser = getIntent().getStringExtra("user");
        Log.e("editComments", "dataGet: "+ addUser);
        String addType = getIntent().getStringExtra("type");
        String addStart = getIntent().getStringExtra("start");
        String addEnd = getIntent().getStringExtra("end");
        String addTime = getIntent().getStringExtra("time");

        Button button = (Button) findViewById(R.id.commentsbtn);
        Button button1 = (Button) findViewById(R.id.goBack);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comments = input.getText().toString();
                String temp = comments.toLowerCase();
                boolean searchf = temp.contains("fuck");
                boolean searchb = temp.contains("bitch");
                boolean searchs = temp.contains("shit");
                boolean searchm = temp.contains("moron");
                if(comments.equals("Please leave your comments here") ||(comments.equals(""))){
                    Toast.makeText(editComments.this, "You did not leave any comments", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(editComments.this, UserProfile.class);
                    intent.putExtra("user", addUser);
                    startActivity(intent);
                }
                else {
                    if(searchf || searchs || searchb || searchm){
                        Toast.makeText(editComments.this, "The comment is contains " +
                                "legal words", Toast.LENGTH_SHORT).show();

                    }
                    else if (comments.length() >= 400) {
                        Toast.makeText(editComments.this, "The comment is too long", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(editComments.this, UserProfile.class);
                        intent.putExtra("user", addUser);
                        intent.putExtra("type", addType);
                        intent.putExtra("start", addStart);
                        intent.putExtra("end", addEnd);
                        intent.putExtra("time", addTime);
                        intent.putExtra("comment", comments);
                        startActivity(intent);
                    }
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editComments.this, UserProfile.class);
                intent.putExtra("user", addUser);
                startActivity(intent);
            }
        });
    }
}
