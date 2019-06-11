package com.example.app1;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Date today=new Date(System.currentTimeMillis());
        SimpleDateFormat timeFormat= new SimpleDateFormat("hh:mm dd");
        String s=timeFormat.format(today.getTime());
//        Toast.makeText(context,"" + +ReminderActivity.minutes +"-"+ReminderActivity.hour+"-"+(ReminderActivity.day),Toast.LENGTH_SHORT).show();
//        Log.e(getClass().getName(), "Minute:"+ReminderActivity.minutes +ReminderActivity.hour+ReminderActivity.day );
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
        Log.e(getClass().getName(), s);

    }

}
