package com.example.twilightuser.loginexample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.twilightuser.loginexample.R;

public class Show_password_details extends Activity {

     TextView TextPassword_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_password_details);

        TextPassword_details=(TextView) findViewById(R.id.TextPassword_details);

        String password= getIntent().getExtras().getString("password");

        String password_details=" Your Password is : "+ password;

        TextPassword_details.setText(password_details);
    }
}
