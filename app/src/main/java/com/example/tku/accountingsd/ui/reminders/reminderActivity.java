package com.example.tku.accountingsd.ui.reminders;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tku.accountingsd.Adapter.ReminderAdapter;
import com.example.tku.accountingsd.DBHelper.ReminderDBHelper;
import com.example.tku.accountingsd.R;

public class reminderActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReminderDBHelper dbHelper;
    private ReminderAdapter adapter;
    private String filter = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        mRecyclerView = (RecyclerView) findViewById(R.id.reminderRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent();
                i.setClass(reminderActivity.this, addReminderActivity.class);
                startActivity(i);
                //Toast.makeText(periodReminder.this, "Fab clicked", Toast.LENGTH_LONG).show();
            }
        });

        //populate recyclerview
        populaterecyclerView(filter);
    }



    private void populaterecyclerView(String filter) {
        dbHelper = new ReminderDBHelper(this);
        adapter = new ReminderAdapter(dbHelper.reminderList(filter), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }
}
