package com.example.router_route_creation;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class AccountCreate extends AppCompatActivity {
    LogInDataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_create);
        db = new LogInDataBaseHelper(this);
        EditText user = (EditText) findViewById(R.id.cUser);
        EditText pass = (EditText) findViewById(R.id.cPass);
        EditText rPass = (EditText) findViewById(R.id.cPass2);
        Button signUp = (Button) findViewById(R.id.signup);
        Button goBack = (Button) findViewById(R.id.goBack);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = user.getText().toString();
                String password = pass.getText().toString();
                String rPassword = rPass.getText().toString();

                if(userName.equals("") || password.equals("") || rPassword.equals("") ){
                    Toast.makeText(AccountCreate.this, "Please enter all the fields", LENGTH_SHORT).show();
                }
                else{
                    if(password.length() <= 3){
                        Toast.makeText(AccountCreate.this, "Password too short", LENGTH_SHORT).show();
                    }
                    if(password.equals(rPassword)){
                        boolean checkUser = db.checkUsername(userName);
                        if(!checkUser){
                            db.addContent(userName, password);
                            Toast.makeText(AccountCreate.this, "Sign up success", LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LogIn.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(AccountCreate.this, "Username occupied. Please use a different Username", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(AccountCreate.this, "Password does not match.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountCreate.this, LogIn.class);
                startActivity(intent);
            }
        });



    }
}
