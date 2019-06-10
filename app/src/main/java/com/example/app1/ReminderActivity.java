package com.example.app1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {
    private TimePicker timePicker;
    private Spinner spinnerRepeat;
    private Button btSave;
    private String repeat[] = {"Daily", "Weekly", "Monthly"};
    private PendingIntent pendingIntent;
    //private int day, hour, minutes;
    private Calendar calendar;
    private AlarmManager manager;
    private Intent intent;
    private String TAG;
    public static int day,hour;
    public static   int minutes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remider);
        init();
    }

    private void init() {
        timePicker = findViewById(R.id.timePicker);
        spinnerRepeat = findViewById(R.id.id_spinner_repeat);
        btSave = findViewById(R.id.bt_save);
        btSave.setOnClickListener(this);
        calendar = Calendar.getInstance();
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        setupTimePicker();
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, repeat);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerRepeat.setAdapter(adapter);
        spinnerRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public void setupTimePicker() {
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                day= hourOfDay;
                minutes = minute;
            }
        });
    }

    @Override
    public void onClick(View v) {
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        intent = new Intent(this, AlarmReceiver.class);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = timePicker.getCurrentHour();
        minutes = timePicker.getCurrentMinute();
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Toast.makeText(ReminderActivity.this, "It's :" + timePicker.getCurrentHour() + "h:" + timePicker.getCurrentMinute() + "minutes", Toast.LENGTH_SHORT).show();

        if(spinnerRepeat.getSelectedItem().toString().equalsIgnoreCase("Daily"))
        {
            //manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else if(spinnerRepeat.getSelectedItem().toString().equalsIgnoreCase("Weekly"))
        {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60*12*7, pendingIntent);

        }else if (spinnerRepeat.getSelectedItem().toString().equalsIgnoreCase("Monthly"))
        {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60*12*7*4, pendingIntent);
        }



    }



}
