package com.example.router_route_creation;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {
    LogInDataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        db = new LogInDataBaseHelper(this);
        EditText user = (EditText) findViewById(R.id.fUser);
        EditText pass = (EditText) findViewById(R.id.fPass);
        EditText rPass = (EditText) findViewById(R.id.fPass2);
        Button reset = (Button) findViewById(R.id.reset);
        Button goBack = (Button) findViewById(R.id.goBack);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = user.getText().toString();
                String password = pass.getText().toString();
                String rPassword = rPass.getText().toString();

                if(userName.equals("") || password.equals("") || rPassword.equals("") ){
                    Toast.makeText(ForgotPassword.this, "Please enter all the fields", LENGTH_SHORT).show();
                }
                else{
                    if(password.length() <= 3){
                        Toast.makeText(ForgotPassword.this, "Password too short", LENGTH_SHORT).show();
                    }
                    if(password.equals(rPassword)){
                        boolean checkUser = db.checkUsername(userName);
                        if(checkUser){
                            db.update(userName, password);
                            Toast.makeText(ForgotPassword.this, "Password reset success", LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LogIn.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(ForgotPassword.this, "Username does not exist", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(ForgotPassword.this, "Password does not match.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, LogIn.class);
                startActivity(intent);
            }
        });


    }
}
