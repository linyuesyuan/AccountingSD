package com.example.tku.accountingsd.ui.statistics;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.tku.accountingsd.Adapter.NewRecordAdapter;
import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.ui.DialogManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PieChartFragment extends Fragment {

    PieChart mPieChart;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    CategoriesDBHelper dbHelper;
    NewRecordDBHelper newRecordDBHelper;
    NewRecordAdapter newRecordAdapter;

    Button bt_fromDate, bt_toDate;

    String toDate, fromDate;
    int categoriesIndex;
    SparseArray<Float> sumOfCategories;
    SparseArray<String> categoriesTitle;
    SparseArray<Integer> colorIndex;

    public PieChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        mPieChart = (PieChart)v.findViewById(R.id.pieChart);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        /*
        bt_fromDate = (Button) v.findViewById(R.id.bt_fromDate);
        bt_toDate = (Button) v.findViewById(R.id.bt_toDate);

        bt_toDate.setBackgroundColor(Color.TRANSPARENT);
        bt_fromDate.setBackgroundColor(Color.TRANSPARENT);
        bt_toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        setFirstOfMonth();
        setLastOfMonth();
        */
        fillChart();

        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String categories = ((PieEntry) e).getLabel();
                categoriesIndex = categoriesTitle.indexOfValue(categories)+1;
                populateRecyclerView();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return v;
    }

    private void populateRecyclerView(){
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        newRecordAdapter = new NewRecordAdapter(newRecordDBHelper.recordListByCategories(categoriesIndex), getActivity(), recyclerView);
        recyclerView.setAdapter(newRecordAdapter);
    }

    private void fillChart(){
        newRecordDBHelper = new NewRecordDBHelper(getActivity());
        dbHelper = new CategoriesDBHelper(getActivity());
        sumOfCategories = newRecordDBHelper.loadPeiChartData();
        categoriesTitle = dbHelper.loadCategoriesTitle();
        colorIndex = dbHelper.loadColor();
        List<Integer> colors = new ArrayList<>();
        List<PieEntry> entries = new ArrayList<>();

        for(int i =0; i<sumOfCategories.size(); i++){
            String categories = categoriesTitle.valueAt(sumOfCategories.keyAt(i)-1);
            float sum = sumOfCategories.valueAt(i);
            int color = colorIndex.valueAt(sumOfCategories.keyAt(i)-1);
            colors.add(color);
            entries.add(new PieEntry(sum, categories));
        }
        PieDataSet set = new PieDataSet(entries, null);
        set.setColors(colors);
        PieData data = new PieData(set);
        mPieChart.setData(data);
        mPieChart.invalidate();// refresh
    }

    private void setFirstOfMonth() {

        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);

        toDate = yy + "-" + (mm + 1) + "-" + dd;
        bt_fromDate.setText(toDate);
    }
    private void setLastOfMonth() {

        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 0);
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);

        fromDate = yy + "-" + (mm + 1) + "-" + dd;
        bt_toDate.setText(fromDate);
    }

    private void showDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        DialogManager.getInstance().showDatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                String date = year + "-" + (month + 1) + "-" + day;
                bt_fromDate.setText(date);
            }
        }, calendar);
    }

}
