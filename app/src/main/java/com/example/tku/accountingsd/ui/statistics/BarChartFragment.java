package com.example.tku.accountingsd.ui.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarChartFragment extends Fragment {

    BarChart mBarChart;
    CategoriesDBHelper dbHelper;
    NewRecordDBHelper newRecordDBHelper;

    public BarChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bar_chart, container, false);
        mBarChart = v.findViewById(R.id.mBarChart);

        // Inflate the layout for this fragment

        fillBarChart();
        return v;
    }

    private void fillBarChart(){
        newRecordDBHelper = new NewRecordDBHelper(getActivity());
        dbHelper = new CategoriesDBHelper(getActivity());
        SparseArray<Float> sumOfCategories = newRecordDBHelper.loadPeiChartData();
        SparseArray<String> categoriesTitle = dbHelper.loadCategoriesTitle();
        List<BarEntry> entries = new ArrayList<>();

        ArrayList xVals = new ArrayList();
        XAxis xAxis = mBarChart.getXAxis();

        for(int i =0; i<sumOfCategories.size(); i++){
            float sum = sumOfCategories.valueAt(i);
            String categories = categoriesTitle.valueAt(sumOfCategories.keyAt(i)-1);
            xVals.add(categories);
            entries.add(new BarEntry(i, sum));
        }

        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);//Set a minimum interval for the axis when zooming in.

        BarDataSet set = new BarDataSet(entries, "");
        set.setColors(ColorTemplate.COLORFUL_COLORS);//

        BarData data = new BarData(set);
        mBarChart.setData(data);

        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getXAxis().setDrawGridLines(false);//不設置X軸網格
        mBarChart.getAxisLeft().setDrawGridLines(false);//不設置Y軸網格
        mBarChart.getAxisRight().setEnabled(false);//右側不顯示Y軸
        mBarChart.getAxisRight().setDrawGridLines(false);
        mBarChart.setFitBars(true); // make the x-axis fit exactly all bars
        mBarChart.notifyDataSetChanged();//Lets the chart know its underlying data has changed and performs all necessary recalculations.
        mBarChart.invalidate(); // refresh
    }

}