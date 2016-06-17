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

public class Change_Password extends Activity implements View.OnClickListener{

    private EditText editTextOldPassword,editTextNewPassword,editTextConfirmPassword;
    Button buttonChangePassword;
    String OldPassword,NewPassword,ConfirmPassword,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);
        init();
    }

    private void init() {
        editTextOldPassword=(EditText) findViewById(R.id.editTextOldPassword);
        editTextNewPassword=(EditText) findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword=(EditText) findViewById(R.id.editTextConfirmPassword);
        buttonChangePassword=(Button) findViewById(R.id.buttonChangePassword);
        buttonChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonChangePassword:
                getUIValues();
                validation();
                break;
        }
    }
    private void getUIValues() {
        OldPassword=editTextOldPassword.getText().toString().trim();
        NewPassword=editTextNewPassword.getText().toString().trim();
        ConfirmPassword=editTextConfirmPassword.getText().toString().trim();
    }

    private void validation() {
        if (OldPassword.length() ==0)
        {
            editTextOldPassword.setError(" Enter your old password");
        }
        else if (NewPassword.length() ==0)
        {
            editTextNewPassword.setError(" Enter your New password");
        }
        else if (ConfirmPassword.length() == 0)
        {
            editTextConfirmPassword.setError(" Enter your Confirm password");
        }
        else
        {
            System.out.println("OldPassword :" + OldPassword);
            System.out.println("NewPassword :" + NewPassword);


            if (!OldPassword.equals(NewPassword))
            {
                System.out.println("ok");

                if (NewPassword.equals(ConfirmPassword))
                {
                    changePassword();
                }
                else
                {
                    editTextOldPassword.setText("");
                    editTextNewPassword.setText("");
                    editTextConfirmPassword.setText("");
                    Toast.makeText(Change_Password.this.getApplicationContext(), " New password and Confirm password \n are Mismatch", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                editTextOldPassword.setText("");
                editTextNewPassword.setText("");
                editTextConfirmPassword.setText("");
                Toast.makeText(Change_Password.this.getApplicationContext(), " Current password and New password \n are same", Toast.LENGTH_LONG).show();
            }
        }


    }

    private void changePassword()
    {

        DatabaseHandler db = new DatabaseHandler(Change_Password.this);

        name= getIntent().getExtras().getString("UserName");

        Registration_Entity change_password = new Registration_Entity();

        change_password.setUser_name(name);
        change_password.setPassword(NewPassword);

        db.ChangePassword(change_password);

        Toast.makeText(Change_Password.this.getApplicationContext(), "Password Change Successfully", Toast.LENGTH_LONG).show();

        Intent myIntent = new Intent(Change_Password.this, MainActivity.class);

        startActivity(myIntent);

        finish();
    }
}
