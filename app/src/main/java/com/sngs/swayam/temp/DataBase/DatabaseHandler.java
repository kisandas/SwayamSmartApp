package com.sngs.swayam.temp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sngs.swayam.temp.Model.Model_Calories;
import com.sngs.swayam.temp.Model.Model_User_Pedometer_data;
import com.sngs.swayam.temp.Model.UserDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "User_Swayam_App.sqlite";
    public static final String TABLE_NAME = "UserList";
    public static final String COLUMN_ID = "UserId";
    public static final String COLUMN_NAME = "UserName";
    public static final String COLUMN_HEIGHT = "Height";
    public static final String COLUMN_WEIGHT = "Weight";
    public static final String COLUMN_AGE = "Age";
    public static final String COLUMN_DATE = "Date";


    //Login
    public static final String TBL_LOGIN = "TBL_LOGIN";
    public static final String L_Id = "L_Id";
    public static final String L_Mobile = "Mobile";
    public static final String L_CODE = "CODE";

    //User Personal Detail
    public static final String TBL_UserDetail = "TBL_UserDetail";
    public static final String U_ID = "ID";
    public static final String U_Name = "Name";
    public static final String U_Mobile = "Mobile";
    public static final String U_Height = "Height";
    public static final String U_Weight = "Weight";
    public static final String U_Age = "Age";


    //Calories
    public static final String TBL_Calories = "TBL_Calories";
    public static final String T_ID = "T_ID";
    public static final String T_Categories = "T_Categories";
    public static final String T_ItemName = "T_ItemName";
    public static final String T_Qty = "T_Qty";
    public static final String T_Calories = "T_Calories";
    public static final String T_Imagename = "T_Imagename";

    //Calories Category
    public static final String TBL_Calories_Category = "TBL_Calories_Category";
    public static final String C_ID = "C_ID";
    public static final String C_Categoryname = "C_Categoryname";

    //User_calory_Detail
    public static final String TBL_User_Pedometer_Detail = "TBL_User_Pedometer_Detail";
    public static final String UP_ID = "UP_ID";
    public static final String UP_Mobile = "UP_Mobile";
    public static final String UP_StepCount = "UP_StepCount";
    public static final String UP_DistanceCount = "UP_DistanceCount";
    public static final String UP_Calory = "UP_Calory";
    public static final String UP_Water = "UP_Water";
    public static final String UP_Date = "UP_Date";

    private Context context;

    private static final String CREATE_LOGIN = "CREATE TABLE " + TBL_LOGIN +
            " (" + L_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + L_Mobile + " TEXT," + L_CODE + " TEXT " + ")";

    private static final String CREATE_UserDetail = "CREATE TABLE " + TBL_UserDetail +
            " (" + U_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + U_Name + " TEXT," + U_Mobile + " TEXT,"
            + U_Height + " TEXT ," + U_Weight + " TEXT ," + U_Age + " TEXT "
            + ")";
    private static final String CREATE_Calories = "CREATE TABLE " + TBL_Calories +
            " (" + T_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + T_Categories + " TEXT," + T_ItemName + " TEXT," + T_Qty + " TEXT ," +
            T_Calories + " TEXT ," + T_Imagename + " TEXT" + ")";

    private static final String CREATE_Calories_Category = "CREATE TABLE " + TBL_Calories_Category +
            " (" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + C_Categoryname + " TEXT" + ")";

    private static final String CREATE_User_Pedometer_Detail = "CREATE TABLE " + TBL_User_Pedometer_Detail +
            " (" + UP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + UP_Mobile + " TEXT ," + UP_StepCount + " TEXT ," + UP_DistanceCount
            + " TEXT ," + UP_Calory + " TEXT ," + UP_Water + " TEXT ," + UP_Date + " TEXT " + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL(CREATE_LOGIN);
            db.execSQL(CREATE_UserDetail);
            db.execSQL(CREATE_Calories);
            db.execSQL(CREATE_Calories_Category);
            db.execSQL(CREATE_User_Pedometer_Detail);

        } catch (Exception e) {
            Log.e("CUSTOM---->", "onCreate:-- " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_LOGIN);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_UserDetail);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_Calories);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_Calories_Category);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_User_Pedometer_Detail);
            onCreate(db);
        } catch (Exception e) {
            Log.e("CUSTOM---->", "onUpgrade:-- " + e.getMessage());
        }
    }

    public void Add_LOGIN(UserDetails user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(L_Mobile, user.getL_Mobile());
        values.put(L_CODE, user.getL_Code());
        db.insert(TBL_LOGIN, null, values);
        db.close(); // Closing database connection

    }

    public void Add_Calories_Category(Model_Calories model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_Categoryname, model.getT_Categories());
        db.insert(TBL_Calories_Category, null, values);
        db.close(); // Closing database connection

    }

    public void Add_Calories(Model_Calories model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T_Categories, model.getT_Categories());
        values.put(T_ItemName, model.getT_ItemName());
        values.put(T_Qty, model.getT_Qty());
        values.put(T_Calories, model.getT_Calories());
        values.put(T_Imagename, model.getT_Calories());
        db.insert(TBL_Calories, null, values);
        db.close(); // Closing database connection

    }

    public void Add_UserDetail(UserDetails user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(U_Name, user.getUserName());
        values.put(U_Mobile, user.getUserMobile());
        values.put(U_Height, user.getUserHeight());
        values.put(U_Weight, user.getUserWeight());
        values.put(U_Age, user.getUserAge());
        db.insert(TBL_UserDetail, null, values);
        db.close(); // Closing database connection
    }

    public void Add_User_Pedometer_Detail(Model_User_Pedometer_data model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UP_Mobile, model.getStrMobile());
        values.put(UP_StepCount, model.getStrStepCount());
        values.put(UP_DistanceCount, model.getStrDistanceCount());
        values.put(UP_Calory, model.getStrCalory());
        values.put(UP_Water, model.getStrWater());
        values.put(UP_Date, model.getStrDate());
        db.insert(TBL_User_Pedometer_Detail, null, values);
        db.close(); // Closing database connection
    }

    public boolean UPDATE_User_Pedometer_Detail(String TodayDate, String Update_Water) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(UP_Water, Update_Water);
        return db.update(TBL_User_Pedometer_Detail, args, UP_Date + "=" + " '" + TodayDate + "' ", null) > 0;
    }

    public List<Model_User_Pedometer_data> GET_User_Pedometer_Detail() {
        List<Model_User_Pedometer_data> userList = new ArrayList<Model_User_Pedometer_data>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TBL_User_Pedometer_Detail;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Model_User_Pedometer_data user = new Model_User_Pedometer_data();
                user.setStrStepCount(cursor.getString(2));
                user.setStrDistanceCount(cursor.getString(3));
                user.setStrCalory(cursor.getString(4));
                user.setStrWater(cursor.getString(5));
                user.setStrDate(cursor.getString(6));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
    }


    public List<Model_User_Pedometer_data> GET_User_MainCount(String FirstDate, String LastDate) {
        List<Model_User_Pedometer_data> userList = new ArrayList<Model_User_Pedometer_data>();
        SQLiteDatabase db = this.getWritableDatabase();
        int U_ID = 0;
        String selectQuery = "SELECT " + UP_ID + " FROM " + TBL_User_Pedometer_Detail + " WHERE " + UP_Date +
                " = " + " '" + FirstDate + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                U_ID = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        System.out.println("User_" + selectQuery);
        String selectQuery2 = "SELECT * FROM " + TBL_User_Pedometer_Detail + " WHERE " + UP_ID +
                " >= " + " '" + U_ID + "' ";
        SQLiteDatabase db2 = this.getWritableDatabase();
        System.out.println("User2_" + selectQuery2);
        Cursor cursor2 = db2.rawQuery(selectQuery2, null);
        if (cursor2.moveToFirst()) {
            do {
                Model_User_Pedometer_data user = new Model_User_Pedometer_data();
                user.setStrStepCount(cursor2.getString(2));
                user.setStrCalory(cursor2.getString(4));
                System.out.println("User_" + cursor2.getString(2));
                userList.add(user);
            } while (cursor2.moveToNext());
        }
        return userList;
    }


    public List<Model_User_Pedometer_data> GET_Week_Of_list(String FirstDate, String LastDate) {
        List<Model_User_Pedometer_data> userList = new ArrayList<Model_User_Pedometer_data>();
        SQLiteDatabase db = this.getWritableDatabase();
        int U_ID = 0;
        String selectQuery = "SELECT " + UP_ID + " FROM " + TBL_User_Pedometer_Detail + " WHERE " + UP_Date +
                " = " + " '" + FirstDate + "'";
        Cursor cursor2 = db.rawQuery(selectQuery, null);
        if (cursor2.moveToFirst()) {
            do {
                U_ID = Integer.parseInt(cursor2.getString(0));
            } while (cursor2.moveToNext());
        }
        System.out.println("User_" + selectQuery);
        String selectQuery2 = "SELECT * FROM " + TBL_User_Pedometer_Detail + " WHERE " + UP_ID +
                " >= " + " '" + U_ID + "' ";

        Cursor cursor = db.rawQuery(selectQuery2, null);
        System.out.println("GET_User_Week_Of_list" + selectQuery2);

        if (cursor.moveToFirst()) {
            do {
                Model_User_Pedometer_data user = new Model_User_Pedometer_data();
                user.setStrStepCount(cursor.getString(2));
                user.setStrDistanceCount(cursor.getString(3));
                user.setStrCalory(cursor.getString(4));
                user.setStrWater(cursor.getString(5));
                user.setStrDate(cursor.getString(6));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
    }

    public List<Model_User_Pedometer_data> GET_Custom_Of_list(String FirstDate, String LastDate) {
        List<Model_User_Pedometer_data> userList = new ArrayList<Model_User_Pedometer_data>();
        SQLiteDatabase db = this.getWritableDatabase();
        int U_ID1 = 0, U_ID2 = 0;
        String selectQuery = "SELECT " + UP_ID + " FROM " + TBL_User_Pedometer_Detail + " WHERE " + UP_Date +
                " = " + " '" + FirstDate + "'";
        Cursor cursor2 = db.rawQuery(selectQuery, null);

        if (cursor2.moveToFirst()) {
            do {
                U_ID1 = Integer.parseInt(cursor2.getString(0));
            } while (cursor2.moveToNext());
        }
        System.out.println("GET_User_Week_Of_listU_ID1" + U_ID1);

        String selectQuery2 = "SELECT " + UP_ID + " FROM " + TBL_User_Pedometer_Detail + " WHERE " + UP_Date +
                " = " + " '" + LastDate + "'";
        Cursor cursor3 = db.rawQuery(selectQuery2, null);

        if (cursor3.moveToFirst()) {
            do {
                U_ID2 = Integer.parseInt(cursor3.getString(0));
            } while (cursor3.moveToNext());
        }
        System.out.println("GET_User_Week_Of_listU_ID2-" + U_ID2);
        String selectQuery3 = "";
        if (U_ID2 == 0) {
             selectQuery3 = "SELECT * FROM " + TBL_User_Pedometer_Detail + " WHERE " + UP_ID +
                    " >= " + " '" + U_ID1 + "'";
        }else
        {
            selectQuery3 = "SELECT * FROM " + TBL_User_Pedometer_Detail + " WHERE " + UP_ID +
                    " >= " + " '" + U_ID1 + "'" + " AND " + UP_ID +
                    " <= " + "'" + U_ID2 + "'";
        }

        Cursor cursor = db.rawQuery(selectQuery3, null);
        System.out.println("GET_User_Week_Of_list" + selectQuery3);

        if (cursor.moveToFirst()) {
            do {
                Model_User_Pedometer_data user = new Model_User_Pedometer_data();
                user.setStrStepCount(cursor.getString(2));
                user.setStrDistanceCount(cursor.getString(3));
                user.setStrCalory(cursor.getString(4));
                user.setStrWater(cursor.getString(5));
                user.setStrDate(cursor.getString(6));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
    }

    public List<Model_User_Pedometer_data> GET_User_Week_Of_list(String FirstDate, String LastDate) {
        List<Model_User_Pedometer_data> userList = new ArrayList<Model_User_Pedometer_data>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TBL_User_Pedometer_Detail + " WHERE " + UP_Date +
                " >= " + " '" + FirstDate + "'" + " AND " + UP_Date +
                " <= " + "'" + LastDate + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Model_User_Pedometer_data user = new Model_User_Pedometer_data();
                user.setStrStepCount(cursor.getString(2));
                user.setStrDistanceCount(cursor.getString(3));
                user.setStrCalory(cursor.getString(4));
                user.setStrWater(cursor.getString(5));
                user.setStrDate(cursor.getString(6));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
    }


    public List<Model_User_Pedometer_data> GET_TodayDate(String strDate) {
        List<Model_User_Pedometer_data> userList = new ArrayList<Model_User_Pedometer_data>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TBL_User_Pedometer_Detail + " WHERE " + UP_Date + " = " + " '" + strDate + "' " + "";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Model_User_Pedometer_data user = new Model_User_Pedometer_data();
                user.setStrDate(cursor.getString(6));
                user.setStrStepCount(cursor.getString(2));
                user.setStrDistanceCount(cursor.getString(3));
                user.setStrCalory(cursor.getString(4));
                user.setStrWater(cursor.getString(5));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
    }

    public boolean UPDATE_Today_StepCount(String TodayDate, String Update_StepCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(UP_StepCount, Update_StepCount);
        return db.update(TBL_User_Pedometer_Detail, args, UP_Date + "=" + " '" + TodayDate + "' ", null) > 0;
    }

    public boolean UPDATE_Today_Distance(String TodayDate, String Update_DistanceCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(UP_DistanceCount, Update_DistanceCount);
        return db.update(TBL_User_Pedometer_Detail, args, UP_Date + "=" + " '" + TodayDate + "' ", null) > 0;
    }

    public boolean UPDATE_Today_Calory(String TodayDate, String Update_Calory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(UP_Calory, Update_Calory);
        return db.update(TBL_User_Pedometer_Detail, args, UP_Date + "=" + " '" + TodayDate + "' ", null) > 0;
    }

    public boolean UPDATE_Today_Water(String TodayDate, String Update_Water) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(UP_Water, Update_Water);
        return db.update(TBL_User_Pedometer_Detail, args, UP_Date + "=" + " '" + TodayDate + "' ", null) > 0;
    }

    public List<Model_Calories> GET_CALORIES_CATEGORY() {
        List<Model_Calories> userList = new ArrayList<Model_Calories>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TBL_Calories_Category;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Model_Calories user = new Model_Calories();
                user.setT_Categories(cursor.getString(1));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
    }

    public List<Model_Calories> GET_CALORIES_CATEGORY_DETAIL(String strCATEGORY) {
        List<Model_Calories> userList = new ArrayList<Model_Calories>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TBL_Calories + " WHERE " + T_Categories + " = " + " '" + strCATEGORY + "' " + "";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Model_Calories user = new Model_Calories();
                user.setT_Categories(cursor.getString(1));
                user.setT_ItemName(cursor.getString(2));
                user.setT_Qty(cursor.getString(3));
                user.setT_Calories(cursor.getString(4));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }


    public List<UserDetails> GET_LOGIN_USER(String strMobile) {
        List<UserDetails> userList = new ArrayList<UserDetails>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TBL_LOGIN + " WHERE " + L_Mobile + " = " + " '" + strMobile + "' " + "";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UserDetails user = new UserDetails();
                user.setL_Mobile(cursor.getString(1));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    public List<UserDetails> GET_USER_DETAIL(String strMobile) {
        List<UserDetails> userList = new ArrayList<UserDetails>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TBL_UserDetail + " WHERE " + U_Mobile + " = " + " '" + strMobile + "' " + "";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UserDetails user = new UserDetails();
                user.setUserName(cursor.getString(1));
                user.setUserMobile(cursor.getString(2));
                user.setUserHeight(cursor.getString(3));
                user.setUserWeight(cursor.getString(4));
                user.setUserAge(cursor.getString(5));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    public boolean UPDATE_UserAge(String strMobile, String strAge) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(U_Age, strAge);
        return db.update(TBL_UserDetail, args, U_Mobile + "=" + " '" + strMobile + "' ", null) > 0;
    }

    public boolean UPDATE_UserHeight(String strMobile, String strHeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(U_Height, strHeight);
        return db.update(TBL_UserDetail, args, U_Mobile + "=" + " '" + strMobile + "' ", null) > 0;
    }

    public boolean UPDATE_UserWeight(String strMobile, String strWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(U_Weight, strWeight);
        return db.update(TBL_UserDetail, args, U_Mobile + "=" + " '" + strMobile + "' ", null) > 0;
    }


//    public boolean updateContact(int rowId, int bal) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues args = new ContentValues();
//        args.put(P_OpeningBalance, bal);
//
//        return db.update(PersonalDetail, args, P_ID + "=" + rowId, null) > 0;
//    }

//    public void DeleteMilkRow(String cuscodde) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(tblMilkdata,
//                CUSTMOR_CODE + " = ?  ",
//                new String[]{cuscodde});
//    }
//
//    public void DeleteMilkdata() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS " + tblMilkdata);
//        onCreate(db);
//    }

}
