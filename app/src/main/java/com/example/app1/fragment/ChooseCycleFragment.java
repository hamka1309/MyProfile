package com.example.app1.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.app1.MainActivity;
import com.example.app1.PeriodActivity;
import com.example.app1.R;

public class ChooseCycleFragment extends Fragment implements View.OnClickListener {
private Button btNext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_cycle, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btNext = getActivity().findViewById(R.id.bt_next);
        btNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        PeriodActivity p = (PeriodActivity) getActivity();
        p.showFragment(p.getChooseDayFragment());
    }
}
