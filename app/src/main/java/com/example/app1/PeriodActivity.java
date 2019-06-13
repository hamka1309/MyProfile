package com.example.app1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.app1.fragment.ChooseCycleFragment;
import com.example.app1.fragment.ChooseDayFragment;

public class PeriodActivity extends AppCompatActivity {
private ChooseDayFragment chooseDayFragment;
private ChooseCycleFragment chooseCycleFragment;

    public ChooseDayFragment getChooseDayFragment() {
        return chooseDayFragment;
    }

    public ChooseCycleFragment getChooseCycleFragment() {
        return chooseCycleFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        init();
    }

    private void init() {
        chooseDayFragment = new ChooseDayFragment();
        chooseCycleFragment = new ChooseCycleFragment();

        showFragment(chooseCycleFragment);
    }
    public void showFragment(Fragment fm){
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.container_fragment, fm);
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                Log.e(getClass().getName(), "runOnCommit");
            }
        });
        transaction.commitAllowingStateLoss();
    }

}
