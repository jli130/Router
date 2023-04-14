package com.example.router_route_creation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SavedDatabaseHelper extends SQLiteOpenHelper {
    public static final String dbName = "saved.db";
    public static final String tableName = "saved_table";
    public static final String col1 = "USER";
    public static final String col2 = "TYPE";
    public static final String col3 = "START";
    public static final String col4 = "DESTINATION";
    public static final String col5 = "TIME";


    public SavedDatabaseHelper(Context context){
        super (context, dbName, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + tableName + " (USER TEXT, TYPE TEXT, START TEXT, DESTINATION TEXT, TIME TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    public boolean addContent(String user, String type, String start, String end, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, user);
        contentValues.put(col2, type);
        contentValues.put(col3, start);
        contentValues.put(col4, end);
        contentValues.put(col5, time);
        long result = db.insert(tableName, null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean update(String user, String type, String start, String end, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, user);
        contentValues.put(col2, type);
        contentValues.put(col3, start);
        contentValues.put(col4, end);
        contentValues.put(col5, time);
        db.update(tableName, contentValues, "USER = ? AND TYPE = ? AND START = ? AND DESTINATION = ? AND TIME = ?",
                new String[]{type, start, end, time});
        return true;
    }

    public ArrayList<Routes> getData(){
        ArrayList<Routes> comments = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + tableName, null);
        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Routes comment = new Routes();
                comment.setUser(cursor.getString(0));
                comment.setType(cursor.getString(1));
                comment.setStart(cursor.getString(2));
                comment.setEnd(cursor.getString(3));
                comment.setTime(cursor.getString(4));
                comments.add(comment);
            }
        }
        cursor.close();
        db.close();
        return comments;
    }

    public Integer delete(String user, String type, String start, String end, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, "USER = ? AND TYPE = ? AND START = ? AND DESTINATION = ? AND TIME = ?",
                new String[]{user, type, start, end, time});
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ tableName);
        db.close();
    }
}