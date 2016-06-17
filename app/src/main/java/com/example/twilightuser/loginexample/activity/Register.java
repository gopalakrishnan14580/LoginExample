package com.example.twilightuser.loginexample.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twilightuser.loginexample.R;
import com.example.twilightuser.loginexample.database.DatabaseHandler;
import com.example.twilightuser.loginexample.model.Registration_Entity;

public class Register extends Activity implements View.OnClickListener{

    private EditText editTextName, editTextUsername, editTextPassword, editTextEmail;
    private Button buttonRegister;
    String name, userName, password, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

    }
    private void init() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonRegister:
                getUIValues();
                validation();
                break;
        }
    }

    private void getUIValues() {
        name = editTextName.getText().toString().trim();
        userName = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
    }

    private void validation() {


        if (name.length() == 0) {
           // editTextName.setHintTextColor(getResources().getColor(R.color.colorAccent));
            editTextName.setError(" Enter Name");
        } else if (userName.length() == 0) {
            editTextUsername.setHintTextColor(getResources().getColor(R.color.colorAccent));
        } else if (password.length() == 0) {
            editTextPassword.setHintTextColor(getResources().getColor(R.color.colorAccent));
        } else if (email.length() == 0) {
            editTextEmail.setHintTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            /*Registration for sqlite*/
            registerSqlite();
        }
    }

    private void registerSqlite() {

        try {

            DatabaseHandler db = new DatabaseHandler(Register.this);

            Registration_Entity register = new Registration_Entity();

            register.setName(name);
            register.setUser_name(userName);
            register.setPassword(password);
            register.setEmail(email);

            db.addRegistration(register);

            Toast.makeText(Register.this.getApplicationContext(), "Registration Successfully", Toast.LENGTH_LONG).show();


            Intent myIntent = new Intent(Register.this, MainActivity.class);

            startActivity(myIntent);

            finish();


        } catch (Exception e)

        {
            Toast.makeText(Register.this.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

}
