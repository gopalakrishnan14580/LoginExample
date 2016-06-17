package com.example.twilightuser.loginexample.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twilightuser.loginexample.R;
import com.example.twilightuser.loginexample.database.DatabaseHandler;

public class MainActivity extends Activity implements View.OnClickListener{

    private EditText editTextUserName, editTextPassword;
    Button login_btn, register_btn,forgot_password_btn;
    CheckBox checkRem;
    String UserName, Password;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        init();
    }
    private void init() {
        editTextUserName = (EditText) findViewById(R.id.TextUserName);
        editTextPassword = (EditText) findViewById(R.id.TextPassword);
        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
        forgot_password_btn=(Button) findViewById(R.id.forgot_password_btn);
        checkRem = (CheckBox) findViewById(R.id.chkRem);
try {
    SharedPreferences sh_Pref = context.getSharedPreferences("LoginUserName", Context.MODE_PRIVATE);
    UserName = sh_Pref.getString("userName", "");
    Password = sh_Pref.getString("password", "");

    if (UserName.length() != 0) {
        checkRem.setChecked(true);
    }

    editTextUserName.setText(UserName);
    editTextPassword.setText(Password);
}
catch (Exception e)
{
    System.out.println("Exception : "+e.getMessage());
}

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        forgot_password_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                getUIValues();
                validation();
                break;
            case R.id.register_btn:
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                finish();
                break;
            case R.id.forgot_password_btn:
                Intent forgot_password = new Intent(MainActivity.this, Forgot_password.class);
                startActivity(forgot_password);
                break;
        }
    }

    private void getUIValues() {
        UserName = editTextUserName.getText().toString().trim();
        Password = editTextPassword.getText().toString().trim();
    }

    private void validation() {

        if (UserName.length() == 0) {
            editTextUserName.setHintTextColor(getResources().getColor(R.color.colorAccent));
        } else if (Password.length() == 0) {
            editTextPassword.setHintTextColor(getResources().getColor(R.color.colorAccent));
        } else {

        /*login for sqlite*/
           loginSqlite();
        }

    }

    private void loginSqlite() {


        DatabaseHandler db = new DatabaseHandler(MainActivity.this);


        String storedPassword = db.getLogin(UserName);

        if (Password.equals(storedPassword)) {


            SaveSharedValues(UserName,Password);

        }

        else {
            Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_LONG).show();
        }

    }

    private void SaveSharedValues(String userName ,String password) {


        if (checkRem.isChecked()) {

            SharedPreferences sh_Pref1 = getSharedPreferences( "LoginUserName", MODE_PRIVATE);
            SharedPreferences.Editor toEdit1;

            toEdit1 = sh_Pref1.edit();
            toEdit1.putString("userName", userName);
            toEdit1.putString("password", password);

            toEdit1.commit();
        }else{
            SharedPreferences settings1 = getSharedPreferences("LoginUserName", 0);
            SharedPreferences.Editor editor1 = settings1.edit();
            editor1.clear();
            editor1.commit();
        }

        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();

        Intent main = new Intent(MainActivity.this, ShowDetails.class);
        main.putExtra("UserName", UserName);
        startActivity(main);
        finish();
    }
}
