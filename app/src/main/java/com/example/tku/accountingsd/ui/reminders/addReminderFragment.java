package com.example.tku.accountingsd.ui.reminders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tku.accountingsd.BaseFragment;
import com.example.tku.accountingsd.DBHelper.ReminderDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.interfaces.IUserActionsMode;
import com.example.tku.accountingsd.model.Reminder;
import com.example.tku.accountingsd.ui.DatePickerFragment;
import com.example.tku.accountingsd.ui.DialogManager;

import java.util.Calendar;

public class addReminderFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener{

    public static final String REMINDER_ID_KEY = "_reminder_id";
    private EditText etTitle;
    private TextView tvReminderDatePicker;
    private EditText etDateCycle;
    private EditText etMoney;
    private Button btCreateReminder;

    private ReminderDBHelper dbHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_reminder,container,false);
        etTitle = (EditText) rootView.findViewById(R.id.etTitle);
        tvReminderDatePicker = (TextView) rootView.findViewById(R.id.tvReminderDatePicker);
        etDateCycle = (EditText) rootView.findViewById(R.id.etDateCycle);
        etMoney = (EditText) rootView.findViewById(R.id.etMoney);
        btCreateReminder = (Button) rootView.findViewById(R.id.btCreateReminder);

        setCurrentDay();

        tvReminderDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btCreateReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReminder();
            }
        });
        return rootView;
    }

    static addReminderFragment newInstance(@IUserActionsMode int mode, String reminderId) {
        addReminderFragment addReminderFragment = new addReminderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IUserActionsMode.MODE_TAG, mode);
        if (reminderId != null) bundle.putString(REMINDER_ID_KEY, reminderId);
        addReminderFragment.setArguments(bundle);
        return addReminderFragment;
    }


    private void setCurrentDay() {

        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);

        tvReminderDatePicker.setText(new StringBuilder()
                .append(yy).append("-").append(mm + 1).append("-")
                .append(dd));
    }

    private void saveReminder() {
        String title = etTitle.getText().toString().trim();
        String startDate = tvReminderDatePicker.getText().toString().trim();
        String dateCycle = etDateCycle.getText().toString().trim();
        String money = etMoney.getText().toString().trim();
        dbHelper = new ReminderDBHelper(getActivity());

        if (title.isEmpty()) {
            //error name is empty
            Toast.makeText(getActivity(), "You must enter a title", Toast.LENGTH_SHORT).show();
        }

        if (startDate.isEmpty()) {
            //error name is empty
            Toast.makeText(getActivity(), "You must enter an startDate", Toast.LENGTH_SHORT).show();
        }

        if (dateCycle.isEmpty()) {
            //error name is empty
            Toast.makeText(getActivity(), "You must enter an dateCycle", Toast.LENGTH_SHORT).show();
        }

        if (money.isEmpty()) {
            //error name is empty
            Toast.makeText(getActivity(), "You must enter an money", Toast.LENGTH_SHORT).show();
        }

        //create new person
        Reminder Reminder = new Reminder(title, startDate, dateCycle, money);
        dbHelper.saveNewReminder(Reminder);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    public void goBackHome() {
        mFragmentListener.setResultWithData(Activity.RESULT_OK, new Intent());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = year + "-" + (month + 1) + "-" + dayOfMonth;

        tvReminderDatePicker.setText(currentDateString);
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
                String currentDateString = year + "-" + (month + 1) + "-" + day;

                tvReminderDatePicker.setText(currentDateString);
            }
        }, calendar);
    }
}
