package com.example.tku.accountingsd.ui.reminders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tku.accountingsd.Adapter.ReminderAdapter;
import com.example.tku.accountingsd.BaseFragment;
import com.example.tku.accountingsd.DBHelper.ReminderDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Reminder;

public class reminderFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReminderDBHelper dbHelper;
    private ReminderAdapter adapter;
    private String filter = "";

    
    FloatingActionButton fab;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent();
                i.setClass(getActivity(), addReminderActivity.class);
                startActivity(i);
            }
        });


        populaterecyclerView(filter);
    }

    private static final String TAG="Log";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reminder,container,false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.reminderRecyclerView);
        getActivity().setTitle("週期提醒");
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void populaterecyclerView(String filter) {
        dbHelper = new ReminderDBHelper(getActivity());
        adapter = new ReminderAdapter(dbHelper.reminderList(filter), getActivity(), mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }



}
