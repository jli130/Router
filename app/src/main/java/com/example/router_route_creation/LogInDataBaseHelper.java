package com.example.router_route_creation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class LogInDataBaseHelper extends SQLiteOpenHelper {
    public static final String dbName = "logIn.db";
    public static final String tableName = "logIn_table";
    public static final String col1 = "USER";
    public static final String col2 = "PASSWORD";

    public LogInDataBaseHelper(Context context){
        super (context, dbName, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + tableName + " (USER TEXT primary key, PASSWORD TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    public boolean addContent(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, user);
        contentValues.put(col2, password);
        long result = db.insert(tableName, null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE USER = ?", new String[]{username});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkPassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName +
                " WHERE USER = ? AND PASSWORD = ?", new String[]{username, password});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    public ArrayList<User> getData(){
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + tableName, null);
        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){
                User user = new User();
                user.setUser(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                users.add(user);
            }
        }
        cursor.close();
        db.close();
        return users;
    }


    public boolean update(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, user);
        contentValues.put(col2, password);
        db.update(tableName, contentValues, "USER = ?", new String[]{user});
        return true;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ tableName);
        db.close();
    }


}
