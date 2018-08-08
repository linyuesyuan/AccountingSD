package com.example.tku.accountingsd.ui.cateEdit;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tku.accountingsd.Adapter.CateAdapter;
import com.example.tku.accountingsd.DBHelper.cateDBHelper;
import com.example.tku.accountingsd.R;

public class cateEditActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private cateDBHelper dbHelper;
    private CateAdapter adapter;
    private String filter = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cate_edit);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        populaterecyclerView(filter);
    }

    private void populaterecyclerView(String filter) {
        dbHelper = new cateDBHelper(this);
        adapter = new CateAdapter(dbHelper.cateList(filter), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }
}
