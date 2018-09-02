package com.example.tku.accountingsd.ui.newRecord;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tku.accountingsd.Adapter.NewRecordAdapter;
import com.example.tku.accountingsd.BaseFragment;
import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Categories;
import com.example.tku.accountingsd.model.Record;
import com.example.tku.accountingsd.ui.DialogManager;
import com.example.tku.accountingsd.ui.homeScreen.homeScreenFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.DoubleToIntFunction;

/**
 * A simple {@link Fragment} subclass.
 */
public class expenseFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewRecordDBHelper dbHelper;
    private NewRecordAdapter adapter;
    private String filter = "";

    private EditText etTitle;
    private Button btDatePicker;
    private EditText etMoney;
    private Spinner spinner;
    private Button btCreateRecord;



    long dateMilliSecond;


    private TextView mSumTextView;

    Activity mActivity;

    SparseArray<String> categoriesTitleIdArray;


    public expenseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("收入支出");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_expense, container, false);
        findViewById(v);
        loadSpinnerData();
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        btCreateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpense();
                clearRecord();
                loadSum();
                populateRecyclerView(filter);
            }
        });

        setCurrentDay();

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        populateRecyclerView(filter);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void findViewById(View v){
        etTitle = (EditText) v.findViewById(R.id.etTitle);
        btDatePicker = (Button) v.findViewById(R.id.btDatePicker);
        etMoney = (EditText) v.findViewById(R.id.etMoney);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        btCreateRecord = (Button) v.findViewById(R.id.btCreateRecord);
        mSumTextView = (TextView) v.findViewById(R.id.sum);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
    }

    private void setCurrentDay() {

        final Calendar c = Calendar.getInstance();
        dateMilliSecond = c.getTimeInMillis();
        Date date = new Date(dateMilliSecond);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        btDatePicker.setText(dateFormat.format(date));
        loadSum();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    private void saveExpense() {
        CategoriesDBHelper categoriesDBHelper = new CategoriesDBHelper(getActivity());
        String title = etTitle.getText().toString().trim();
        String date = btDatePicker.getText().toString().trim();
        Float money = Float.parseFloat(etMoney.getText().toString().trim());
        String categories = spinner.getSelectedItem().toString().trim();
        int categoriesIndex = categoriesTitleIdArray.indexOfValue(categories)+1;
        Log.d("categoriesIndex" ,Integer.toString(categoriesIndex));
        int exp_inc = categoriesDBHelper.passBooleanByCategories(categoriesIndex);

        dbHelper = new NewRecordDBHelper(getActivity());

        if (title.isEmpty()) {
            //error name is empty
            Toast.makeText(getActivity(), "You must enter a title", Toast.LENGTH_SHORT).show();
        }
        if (categories.isEmpty()) {
            //error name is empty
            Toast.makeText(getActivity(), "You must enter an type", Toast.LENGTH_SHORT).show();
        }
        if(money.isNaN()){
            Toast.makeText(getActivity(), "你必須輸入金額", Toast.LENGTH_SHORT).show();
        }
        //create new record
        Record Record = new Record(title, date, money, categoriesIndex, exp_inc);
        dbHelper.saveNewRecord(Record);
        populateRecyclerView(filter);
    }

    private void clearRecord(){
        etTitle.getText().clear();
        etMoney.getText().clear();

    }

    private void populateRecyclerView(String filter) {
        dbHelper = new NewRecordDBHelper(getActivity());
        adapter = new NewRecordAdapter(dbHelper.recordList(filter, btDatePicker.getText().toString().trim()), getActivity(), mRecyclerView);
        //Log.d("dateSelected", dateSelected);
        mRecyclerView.setAdapter(adapter);
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
                dateMilliSecond = c.getTimeInMillis();
                Date date = new Date(dateMilliSecond);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
                btDatePicker.setText(dateFormat.format(date));
                populateRecyclerView(filter);
                loadSum();
            }
        }, calendar);
    }

    private void loadSpinnerData() {
        CategoriesDBHelper categoriesDBHelper = new CategoriesDBHelper(getActivity());
        categoriesTitleIdArray = categoriesDBHelper.getAllTitle();
        List<String> title = new ArrayList<>();
        for (int i = 0; i < categoriesTitleIdArray.size(); i++) {
            title.add(categoriesTitleIdArray.valueAt(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, title);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void loadSum(){
        NewRecordDBHelper dbHelper = new NewRecordDBHelper(getActivity());
        List<Double> expense = dbHelper.getSum(btDatePicker.getText().toString().trim());
        double sum=0.0;
        for(int i = 0; i<expense.size(); i++){
            sum += expense.get(i);
            Log.d("sum", Double.toString(sum));
        }
        mSumTextView.setText("$"+Double.toString(sum));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onBackPressed(){
        getFragmentManager().beginTransaction().replace(R.id.content_main, new homeScreenFragment()).commit();
    }
}
