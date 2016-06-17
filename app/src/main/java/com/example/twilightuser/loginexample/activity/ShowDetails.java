package com.example.twilightuser.loginexample.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.twilightuser.loginexample.R;
import com.example.twilightuser.loginexample.database.DatabaseHandler;
import com.example.twilightuser.loginexample.model.Registration_Entity;

import java.util.List;

public class ShowDetails extends Activity {

    private TextView textViewName, textViewUserName, textViewPassword, textViewEmail;
    Button show_changePassword,logout;
    public static List<Registration_Entity> user;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        init();
        show_details();
    }
    private void init() {
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        textViewPassword = (TextView) findViewById(R.id.textViewPassword);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        show_changePassword=(Button) findViewById(R.id.show_changePassword);
        logout=(Button) findViewById(R.id.logout);


    }

    private void show_details() {

        DatabaseHandler db = new DatabaseHandler(ShowDetails.this);

        name= getIntent().getExtras().getString("UserName");

        user = db.get_Password(name);

        for (Registration_Entity users : user) {

            textViewName.setText(users.getName());
            textViewUserName.setText(users.getUser_name());
            textViewPassword.setText(users.getPassword());
            textViewEmail.setText(users.getEmail());

        }

        show_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent main = new Intent(ShowDetails.this, Change_Password.class);
                main.putExtra("UserName", name);
                startActivity(main);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(ShowDetails.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }
}
