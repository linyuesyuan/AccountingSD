package com.example.tku.accountingsd.ui.newRecord;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tku.accountingsd.Adapter.NewRecordAdapter;
import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.R;

public class newRecordActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewRecordDBHelper dbHelper;
    private NewRecordAdapter adapter;
    private String filter = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(newRecordActivity.this, addNewRecordActivity.class);
                startActivity(i);
            }
        });
        populaterecyclerView(filter);
    }

    private void populaterecyclerView(String filter) {
        dbHelper = new NewRecordDBHelper(this);
        adapter = new NewRecordAdapter(dbHelper.recordList(filter), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }
}
