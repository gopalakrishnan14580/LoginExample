package com.example.twilightuser.loginexample.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.twilightuser.loginexample.model.Registration_Entity;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "user.db";

    //Operator Table Name

    private static final String TABLE_REGISTRATION="operator_mst";

    //Operator Table Columns names

    private static final String REGISTRATION_ID = "id";
    private static final String REGISTRATION_NAME = "name";
    private static final String REGISTRATION_USER_NAME = "user_name";
    private static final String REGISTRATION_PASSWORD = "password";
    private static final String REGISTRATION_EMAIL = "email";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_REGISTRATION_TABLE = "CREATE TABLE " + TABLE_REGISTRATION + "(" + REGISTRATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + REGISTRATION_NAME + " TEXT," + REGISTRATION_USER_NAME + " TEXT," + REGISTRATION_PASSWORD + " TEXT," + REGISTRATION_EMAIL + " TEXT )";


        db.execSQL(CREATE_REGISTRATION_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        Log.w(DatabaseHandler.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

        // Drop older table if existed
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
    // Create tables again
      onCreate(db);
    }

    //----------------------------------------------------------------REGISTRATION ---------------------------------------------------
    public void addRegistration(Registration_Entity registration) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(REGISTRATION_NAME, registration.getName());
        values.put(REGISTRATION_USER_NAME, registration.getUser_name());
        values.put(REGISTRATION_PASSWORD,registration.getPassword());
        values.put(REGISTRATION_EMAIL,registration.getEmail());

        db.insert(TABLE_REGISTRATION, null, values);
        db.close();
    }
 //----------------------------------------------------------------------LOGIN --------------------------------------------------------------


    public String getLogin(String userName) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_REGISTRATION, null, "user_name=?",
                new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("password"));
        cursor.close();
        return password;
    }
/*
    public Registration_Entity getAll() {
        Registration_Entity a = new Registration_Entity();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REGISTRATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            a.setId(cursor.getInt(0));
            a.setName(cursor.getString(1));
            a.setUser_name(cursor.getString(2));
            a.setPassword(cursor.getString(3));
            a.setEmail(cursor.getString(4));

        }
        cursor.close();
        db.close();
        // return area list
        return a;
    }
*/


    public List<Registration_Entity> getAllUsers() {
        List<Registration_Entity> regList = new ArrayList<Registration_Entity>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REGISTRATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Registration_Entity a = new Registration_Entity();
                a.setId(cursor.getInt(0));
                a.setName(cursor.getString(1));
                a.setUser_name(cursor.getString(2));
                a.setPassword(cursor.getString(3));
                a.setEmail(cursor.getString(4));
                regList.add(a);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        // return area list
        return regList;
    }

  //  --------------------------------------------

    public List<Registration_Entity> get_Password(String name) {
        List<Registration_Entity> regList = new ArrayList<Registration_Entity>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REGISTRATION +" where user_name='"+ name+"'";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Registration_Entity a = new Registration_Entity();
                a.setId(cursor.getInt(0));
                a.setName(cursor.getString(1));
                a.setUser_name(cursor.getString(2));
                a.setPassword(cursor.getString(3));
                a.setEmail(cursor.getString(4));
                regList.add(a);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        // return  list
        return regList;
    }
    //---------------------------------------------------------CHANGE PASSWORD-----------------------------------------------------------------------
    public int ChangePassword(Registration_Entity reg) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REGISTRATION_PASSWORD,reg.getPassword());

        // updating row
        return db.update(TABLE_REGISTRATION, values, REGISTRATION_USER_NAME + " = ?",
                new String[] { String.valueOf(reg.getUser_name()) });
    }


}