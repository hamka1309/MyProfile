package com.example.app1;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private Button btOk, btRemider;
private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        btRemider=findViewById(R.id.bt_remider);
        btRemider.setOnClickListener(this);
        btOk= findViewById(R.id.bt_ok);
        btOk.setOnClickListener(this);

        toolbar= findViewById(R.id.toobal);
        toolbar.setTitle(R.string.profile_title);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_ok:
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_remider:
                Intent intent1 = new Intent(MainActivity.this, ReminderActivity.class);
                startActivity(intent1);
                break;
        }

    }
}
