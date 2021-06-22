package com.sngs.swayam.temp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class myDbAdapter {
    DatabaseHandler myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new DatabaseHandler(context);
    }

    public long insertData(String uid, String name, String height, String weight, String age, Date date)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.COLUMN_ID, uid);
        contentValues.put(DatabaseHandler.COLUMN_NAME, name);
        contentValues.put(DatabaseHandler.COLUMN_HEIGHT, height);
        contentValues.put(DatabaseHandler.COLUMN_WEIGHT, weight);
        contentValues.put(DatabaseHandler.COLUMN_AGE, age);
        contentValues.put(DatabaseHandler.COLUMN_DATE, String.valueOf(date));

        long id = dbb.insert(DatabaseHandler.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {DatabaseHandler.COLUMN_ID,DatabaseHandler.COLUMN_NAME,DatabaseHandler.COLUMN_HEIGHT,DatabaseHandler.COLUMN_WEIGHT,DatabaseHandler.COLUMN_AGE};
        Cursor cursor =db.query(DatabaseHandler.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(DatabaseHandler.COLUMN_ID));
            String name =cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_NAME));
            String  height =cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_HEIGHT));
            String  weight =cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_WEIGHT));
            String  age =cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_AGE));
            String  date =cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_DATE));
            buffer.append(cid+ "   " + name + "   " + height + "   " + weight + "   " + age +"  "+date+" \n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(DatabaseHandler.TABLE_NAME ,DatabaseHandler.COLUMN_NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.COLUMN_NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(DatabaseHandler.TABLE_NAME,contentValues, DatabaseHandler.COLUMN_NAME+" = ?",whereArgs );
        return count;
    }
}
