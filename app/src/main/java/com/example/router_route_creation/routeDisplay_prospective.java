package com.example.router_route_creation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class routeDisplay_prospective extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_display_prosepctive);
        String type = getIntent().getStringExtra("type");
        String start = getIntent().getStringExtra("start_dest");
        String end = getIntent().getStringExtra("end_dest");
        String time = getIntent().getStringExtra("start_time");
        String endTime = getIntent().getStringExtra("end_time");
        String direction = getIntent().getStringExtra("directions");
        String finalTime = time + " - " + endTime;
        TextView textView1 = (TextView) findViewById(R.id.tType);
        TextView textView2 = (TextView) findViewById(R.id.tStart);
        TextView textView3 = (TextView) findViewById(R.id.tEnd);
        TextView textView4 = (TextView) findViewById(R.id.tTime);
        TextView textView5 = (TextView) findViewById(R.id.directions);

        Button button1 = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);


        textView1.setText(type);
        textView2.setText(start);
        textView3.setText(end);
        textView4.setText(finalTime);
        textView5.setText(direction);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(routeDisplay_prospective.this, route_creation_prospective.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(routeDisplay_prospective.this, LogIn.class);
                startActivity(intent);
            }
        });
    }
}
