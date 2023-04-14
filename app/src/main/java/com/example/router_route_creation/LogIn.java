package com.example.router_route_creation;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {
    LogInDataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        db = new LogInDataBaseHelper(this);
        EditText username = (EditText) findViewById(R.id.username); // get the username
        EditText password = (EditText) findViewById(R.id.password); // get the password
        TextView forgot = (TextView) findViewById(R.id.forgotpass);
        TextView create = (TextView) findViewById(R.id.create);
        TextView continueBtn = (TextView) findViewById(R.id.continueWithoutBtn); // if the user want
                                                                                // to continue without an account
        Button loginButton = (Button) findViewById(R.id.loginbtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if the button is clicked, bring the user to main activity
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(user.equals("") || pass.equals("")){
                    Toast.makeText(LogIn.this, "Please enter all the fields", LENGTH_SHORT).show();
                }
                else{
                    boolean checkUserPass = db.checkPassword(user, pass);
                        if(checkUserPass){ //check if the password matches
                                            // if so, go to main activity.
                            Toast.makeText(LogIn.this, "Log in successful", LENGTH_SHORT).show();
                            Intent intent = new Intent(LogIn.this, MainActivity.class);
                            intent.putExtra("user", user);
                            intent.putExtra("password", pass);
                            Log.e("LogIn", "dataPassed: " + user);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(LogIn.this, "Username or Password Incorrect", LENGTH_SHORT).show();
                        }
                    }
                }
        });

        SpannableString ss = new SpannableString(continueBtn.getText().toString());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent2 = new Intent(LogIn.this, route_creation_prospective.class);
                startActivity(intent2);
            }
        };
        ss.setSpan(clickableSpan, 0, continueBtn.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        continueBtn.setText(ss);
        continueBtn.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString ss2 = new SpannableString(forgot.getText().toString());
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent3 = new Intent(LogIn.this, ForgotPassword.class);
                startActivity(intent3);
            }
        };
        ss2.setSpan(clickableSpan2, 0, forgot.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgot.setText(ss2);
        forgot.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString ss3 = new SpannableString(create.getText().toString());
        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent3 = new Intent(LogIn.this, AccountCreate.class);
                startActivity(intent3);
            }
        };
        ss3.setSpan(clickableSpan3, 0, create.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        create.setText(ss3);
        create.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
