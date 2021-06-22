package com.sngs.swayam.temp.pademeter;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.sngs.swayam.temp.BuildConfig;
import com.sngs.swayam.temp.DataBase.DatabaseHandler;
import com.sngs.swayam.temp.Model.Model_User_Pedometer_data;
import com.sngs.swayam.temp.pademeter.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.sngs.swayam.temp.pademeter.util.API26Wrapper.NOTIFICATION_CHANNEL_ID;

public class Broadcast_Save_Userdata extends BroadcastReceiver {
    DatabaseHandler db;
    SharedPreferences pref;
    String mobile;

    @Override
    public void onReceive(Context context, Intent intent) {
        db = new DatabaseHandler(context);
        pref = context.getApplicationContext().getSharedPreferences("Swayam Smart", 0); // 0 - for private mode
        mobile = pref.getString("User_no", "");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        String SystemTime = sdf.format(Calendar.getInstance().getTime());
        System.out.println("SET TIME=" + SystemTime);
        if (SystemTime.equals("23:58")) {
            sdf = new SimpleDateFormat("dd MMM yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            String Today_Date = sdf.format(calendar.getTime());
            String Pre_Date = sdf.format(Calendar.getInstance().getTime());
            List<Model_User_Pedometer_data> listdate = db.GET_TodayDate(Today_Date);
            if (listdate.size() == 0) {
                db.Add_User_Pedometer_Detail(new Model_User_Pedometer_data(mobile, "0", "0", "0", "0", Today_Date));
            }
        }


    }

}