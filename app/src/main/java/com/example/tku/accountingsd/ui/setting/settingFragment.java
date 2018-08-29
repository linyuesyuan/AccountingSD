package com.example.tku.accountingsd.ui.setting;


import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.R;

import java.io.File;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class settingFragment extends Fragment {

    NewRecordDBHelper dbHelper;

    String filter = "";


    public settingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("設定");
        // Inflate the layout for this fragment

        /*
        dbHelper = new NewRecordDBHelper(getActivity());
        final Cursor cursor = dbHelper.getExpenseData();

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "myData.xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("userList", 0);
            // column and row
            sheet.addCell(new Label(0, 0, "UserName"));
            sheet.addCell(new Label(1, 0, "PhoneNumber"));

            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("user_name"));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));

                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, name));
                    sheet.addCell(new Label(1, i, phoneNumber));
                } while (cursor.moveToNext());
            }

            //closing cursor
            cursor.close();
            workbook.write();
            workbook.close();
            Toast.makeText(getActivity(),
                    "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();

        } catch(Exception e) {
            e.printStackTrace();
        }
*/

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

}
