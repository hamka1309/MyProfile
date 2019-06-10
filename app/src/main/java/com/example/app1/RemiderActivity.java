package com.example.app1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class RemiderActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Spinner spinnerRepeat;
    private String repeat[] = {"Daily", "Weekly","Monthly"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remider);
        init();
    }

    private void init() {
        timePicker = findViewById(R.id.timePicker);
        spinnerRepeat=findViewById(R.id.id_spinner_repeat);
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
    }

    public void setupTimePicker(){
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(RemiderActivity.this, "It's :"+hourOfDay+"h:"+minute+"minutes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
