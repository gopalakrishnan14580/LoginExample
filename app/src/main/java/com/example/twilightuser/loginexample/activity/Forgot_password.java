package com.example.twilightuser.loginexample.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.twilightuser.loginexample.R;
import com.example.twilightuser.loginexample.database.DatabaseHandler;
import com.example.twilightuser.loginexample.model.Registration_Entity;

import java.util.List;

public class Forgot_password extends Activity implements View.OnClickListener {

    private EditText editTextUsername_et;
    Button buttonForgotPassword;
    private String username;
    public static List<Registration_Entity> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
    }

    private void init() {

        editTextUsername_et=(EditText) findViewById(R.id.editTextUsername_et);
        buttonForgotPassword=(Button) findViewById(R.id.buttonForgotPassword);
        buttonForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonForgotPassword:
                getUIValues();
                validation();
                break;
        }

    }
    private void getUIValues() {

        username=editTextUsername_et.getText().toString().trim();

    }
    private void validation() {

        if (username.length() == 0)
        {
            editTextUsername_et.setError("Enter your username");
        }
        else
        {
            forgotPassword();
        }
    }

    private void forgotPassword() {

        DatabaseHandler db = new DatabaseHandler(Forgot_password.this);

        user = db.get_Password(username);

        String password="";

        for (Registration_Entity users : user) {

            password=users.getPassword();
        }

        Intent main = new Intent(Forgot_password.this, Show_password_details.class);
        main.putExtra("password", password);
        startActivity(main);
        finish();

    }


}
