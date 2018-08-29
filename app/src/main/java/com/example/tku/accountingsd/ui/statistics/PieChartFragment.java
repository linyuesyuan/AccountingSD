package com.example.tku.accountingsd.ui.statistics;


import android.app.DatePickerDialog;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Record;
import com.example.tku.accountingsd.ui.DialogManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.images.internal.ColorFilters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PieChartFragment extends Fragment {

    PieChart mPieChart;
    CategoriesDBHelper dbHelper;
    NewRecordDBHelper newRecordDBHelper;
    List<Record> mRecordList;

    Button bt_fromDate, bt_toDate;

    String toDate, fromDate;

    String TAG = "測試";

    String today = "2018-8-27";
    String filter = "";

    public PieChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        mPieChart = (PieChart)v.findViewById(R.id.pieChart);
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

        return v;
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
