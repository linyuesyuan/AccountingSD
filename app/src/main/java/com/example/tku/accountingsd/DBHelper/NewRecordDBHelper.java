package com.example.tku.accountingsd.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.tku.accountingsd.model.Record;

import java.util.LinkedList;
import java.util.List;

public class NewRecordDBHelper extends SQLiteOpenHelper {

    private static final String database = "NewRecord.db";
    private static final int version = 3;
    public static final String TABLE_NAME = "NewRecord";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_MONEY = "money";
    public static final String COLUMN_TYPE = "type";

    public NewRecordDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public NewRecordDBHelper(Context context){
        this(context, database, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_MONEY + " TEXT NOT NULL, " +
                COLUMN_TYPE + " TEXT NOT NULL)"
        );
    }

    /**create record**/
    public void saveNewRecord(Record Record) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, Record.getTitle());
        values.put(COLUMN_DATE, Record.getDate());
        values.put(COLUMN_MONEY, Record.getMoney());
        values.put(COLUMN_TYPE, Record.getType());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public List<Record> recordList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Record> recordLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Record Record;

        if (cursor.moveToFirst()) {
            do {
                Record = new Record();

                Record.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                Record.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                Record.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                Record.setMoney(cursor.getString(cursor.getColumnIndex(COLUMN_MONEY)));
                Record.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                recordLinkedList.add(Record);
            } while (cursor.moveToNext());
        }


        return recordLinkedList;
    }

    /**Query only 1 record**/
    public Record getRecord(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Record receivedRecord= new Record();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedRecord.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            receivedRecord.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            receivedRecord.setMoney(cursor.getString(cursor.getColumnIndex(COLUMN_MONEY)));
            receivedRecord.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
        }
        return receivedRecord;

    }

    public void deleteRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }
}
