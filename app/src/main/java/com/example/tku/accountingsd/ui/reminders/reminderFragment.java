package com.example.tku.accountingsd.ui.reminders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tku.accountingsd.Adapter.ReminderAdapter;
import com.example.tku.accountingsd.DBHelper.ReminderDBHelper;
import com.example.tku.accountingsd.R;

public class reminderFragment extends Fragment {


    private ReminderDBHelper dbHelper;
    private ReminderAdapter adapter;
    private String filter = "";


    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reminder,container,false);
        getActivity().setTitle("週期提醒");
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new addReminderFragment()).commit();
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}