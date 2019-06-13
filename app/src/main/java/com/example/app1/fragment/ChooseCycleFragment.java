package com.example.app1.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app1.MainActivity;
import com.example.app1.PeriodActivity;
import com.example.app1.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseCycleFragment extends Fragment implements View.OnClickListener {
private Button btNext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_cycle, container, false);
//        String [] values =
//                {"1","2","3","4","5","6","7"};
//        Spinner spinner = (Spinner) v.findViewById(R.id.spinner_day);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner.setAdapter(adapter);
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
