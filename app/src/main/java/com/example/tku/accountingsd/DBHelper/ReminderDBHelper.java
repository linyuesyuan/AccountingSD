package com.example.tku.accountingsd.DBHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.tku.accountingsd.model.Reminder;

import java.util.LinkedList;
import java.util.List;

public class ReminderDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Reminder.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_NAME = "Reminder";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_START_DATE = "startDate";
    private static final String COLUMN_DATE_CYCLE = "dateCycle";
    private static final String COLUMN_MONEY = "money";

    public ReminderDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_START_DATE + " TEXT NOT NULL, " +
                COLUMN_DATE_CYCLE + " TEXT NOT NULL, " +
                COLUMN_MONEY + " TEXT NOT NULL)"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }


    /**create record**/
    public void saveNewReminder(Reminder reminder) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, reminder.getTitle());
        values.put(COLUMN_START_DATE, reminder.getStartDate());
        values.put(COLUMN_DATE_CYCLE, reminder.getDateCycle());
        values.put(COLUMN_MONEY, reminder.getMoney());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }


    public List<Reminder> reminderList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Reminder> reminderLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Reminder reminder;

        if (cursor.moveToFirst()) {
            do {
                reminder = new Reminder();

                reminder.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                reminder.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                reminder.setStartDate(cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE)));
                reminder.setDateCycle(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_CYCLE)));
                reminder.setMoney(cursor.getString(cursor.getColumnIndex(COLUMN_MONEY)));
                reminderLinkedList.add(reminder);
            } while (cursor.moveToNext());
        }


        return reminderLinkedList;
    }


    /**Query only 1 record**/
    public Reminder getReminder(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Reminder receivedReminder= new Reminder();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedReminder.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            receivedReminder.setStartDate(cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE)));
            receivedReminder.setDateCycle(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_CYCLE)));
            receivedReminder.setMoney(cursor.getString(cursor.getColumnIndex(COLUMN_MONEY)));

        }


        return receivedReminder;


    }

    public void deleteReminderRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    public void updateReminderRecord(long reminderId, Context context, Reminder updatedReminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME+" SET title ='"+ updatedReminder.getTitle() + "', startDate ='" + updatedReminder.getStartDate()+ "', dateCycle ='"+ updatedReminder.getDateCycle() + "', money ='"+  updatedReminder.getMoney() + "'  WHERE _id='" + reminderId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();


    }

}
