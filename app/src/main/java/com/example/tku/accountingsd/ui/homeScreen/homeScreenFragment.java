package com.example.tku.accountingsd.ui.homeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.ui.newRecord.expenseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class homeScreenFragment extends Fragment {

    public static final int RQ_HOME_SCREEN = 1002;
    Button newButton;
    TextView tvSum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_screen, container,false);
        newButton = (Button)rootView.findViewById(R.id.new_button);
        tvSum = (TextView) rootView.findViewById(R.id.tv_sum);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new expenseFragment()).commit();
            }
        });
        loadSum();


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("一起記帳趣！");
    }


    private void loadSum(){
        final Calendar c = Calendar.getInstance();
        Date date = new Date(c.getTimeInMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        String month = dateFormat.format(date);
        Log.d("month", month);
        NewRecordDBHelper dbHelper = new NewRecordDBHelper(getActivity());
        List<Double> expense = dbHelper.getDataByMonth(month);
        Log.d("expense.size", Integer.toString(expense.size()));
        double sum=0.0;
        for(int i = 0; i<expense.size(); i++){
            sum += expense.get(i);
            Log.d("home", Double.toString(sum));
        }
        tvSum.setText("本月損益："+Double.toString(sum));
    }

}
