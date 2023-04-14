package com.example.router_route_creation;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class changePassword extends AppCompatActivity {
    LogInDataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        db = new LogInDataBaseHelper(this);
        String user = getIntent().getStringExtra("user");
        Log.e("changePassword", "dataGet: " + user);
        TextView text = (TextView) findViewById(R.id.user);
        EditText pass = (EditText) findViewById(R.id.fPass);
        EditText rPass = (EditText) findViewById(R.id.fPass2);
        Button change = (Button) findViewById(R.id.reset);
        Button goBack = (Button) findViewById(R.id.goBack);
        text.setText(user);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = pass.getText().toString();
                String rPassword = rPass.getText().toString();

                if (password.equals("") || rPassword.equals("")) {
                    Toast.makeText(changePassword.this, "Please enter all the fields", LENGTH_SHORT).show();
                } else {
                    if(password.length() <= 3){
                        Toast.makeText(changePassword.this, "Password too short", LENGTH_SHORT).show();
                    }
                    else{
                        if (password.equals(rPassword)) {
                            db.update(user, password);
                            Toast.makeText(changePassword.this, "Password change success", LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                            intent.putExtra("user", user);
                            startActivity(intent);

                        } else {
                            Toast.makeText(changePassword.this, "Password does not match.", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(changePassword.this, UserProfile.class);
                intent.putExtra("user", user);

                startActivity(intent);
            }
        });
    }

}
