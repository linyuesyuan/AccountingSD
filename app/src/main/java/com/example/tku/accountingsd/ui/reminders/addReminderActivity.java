package com.example.tku.accountingsd.ui.reminders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tku.accountingsd.BaseActivity;
import com.example.tku.accountingsd.DBHelper.ReminderDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.interfaces.IUserActionsMode;
import com.example.tku.accountingsd.model.Reminder;
import com.example.tku.accountingsd.ui.DatePickerFragment;

import java.util.Calendar;

public class addReminderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        @IUserActionsMode int mode = getIntent().getIntExtra(IUserActionsMode.MODE_TAG, IUserActionsMode.MODE_CREATE);
        String reminderId = getIntent().getStringExtra(addReminderFragment.REMINDER_ID_KEY);
        replaceFragment(addReminderFragment.newInstance(mode, reminderId), false);
    }

}
