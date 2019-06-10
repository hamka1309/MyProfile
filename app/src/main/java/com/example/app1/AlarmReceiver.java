package com.example.app1;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"" + +ReminderActivity.minutes +"-"+ReminderActivity.hour+"-"+(ReminderActivity.day),Toast.LENGTH_SHORT).show();
        Log.e(getClass().getName(), "Minute:"+ReminderActivity.minutes +ReminderActivity.hour+ReminderActivity.day );
    }

}
