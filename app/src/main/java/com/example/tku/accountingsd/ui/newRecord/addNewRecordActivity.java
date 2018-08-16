package com.example.tku.accountingsd.ui.newRecord;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Record;
import com.example.tku.accountingsd.ui.DatePickerFragment;

import java.util.Calendar;

public class addNewRecordActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener{

    private EditText etTitle;
    private TextView tvDatePicker;
    private EditText etMoney;
    private EditText etType;
    private Button btCreateRecord;


    private NewRecordDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);


        etTitle = (EditText) findViewById(R.id.etTitle);
        tvDatePicker = (TextView) findViewById(R.id.tvDatePicker);
        etMoney = (EditText) findViewById(R.id.etMoney);
        etType = (EditText) findViewById(R.id.etType);
        btCreateRecord = (Button) findViewById(R.id.btCreateRecord);


        loadSpinnerData();

        setCurrentDay();

        tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date Picker");
            }
        });


        btCreateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReminder();
            }
        });
    }


    private void setCurrentDay() {

        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);

        tvDatePicker.setText(new StringBuilder()
                .append(yy).append(" ").append("-").append(mm + 1).append("-")
                .append(dd));
    }

    private void saveReminder() {
        String title = etTitle.getText().toString().trim();
        String date = tvDatePicker.getText().toString().trim();
        int money = Integer.parseInt(etMoney.getText().toString());
        String type = etType.getText().toString().trim();
        dbHelper = new NewRecordDBHelper(this);

        if (title.isEmpty()) {
            //error name is empty
            Toast.makeText(this, "You must enter a title", Toast.LENGTH_SHORT).show();
        }

        if (type.isEmpty()) {
            //error name is empty
            Toast.makeText(this, "You must enter an type", Toast.LENGTH_SHORT).show();
        }


        //create new record
        Record Record = new Record(title, date, money, type);
        dbHelper.saveNewRecord(Record);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    public void goBackHome() {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = year + "-" + (month + 1) + "-" + dayOfMonth;

        tvDatePicker.setText(currentDateString);
    }

    private void loadSpinnerData(){
        CategoriesDBHelper db = new CategoriesDBHelper(getApplicationContext());
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String label = adapterView.getItemAtPosition(i).toString();

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
