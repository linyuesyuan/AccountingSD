package com.example.tku.accountingsd.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.tku.accountingsd.model.Categories;
import com.example.tku.accountingsd.model.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class NewRecordDBHelper extends SQLiteOpenHelper {

    private static final String database = "NewRecord.db";
    private static final int version = 3;
    private static final String TABLE_NAME = "NewRecord";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_BOOLEAN = "expense_income";

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
                COLUMN_DATE + " DATE NOT NULL, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_MONEY + " REAL NOT NULL, " +
                COLUMN_BOOLEAN + " INTEGER NOT NULL,"+
                COLUMN_TYPE + " INTEGER NOT NULL)"
        );
    }

    /**create record**/
    public void saveNewRecord(Record Record) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, Record.getDate());
        values.put(COLUMN_BOOLEAN, Record.getExp_inc());
        values.put(COLUMN_TYPE, Record.getType());
        values.put(COLUMN_TITLE, Record.getTitle());
        values.put(COLUMN_MONEY, Record.getMoney());


        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public List<Record> recordList(String filter, String currentDateString) {
        String query;
        if(filter.equals("")){
            query = "SELECT  * FROM " + TABLE_NAME + " WHERE "+ COLUMN_DATE + " = '" + currentDateString + "'";
        }else{
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
                Record.setMoney(cursor.getFloat(cursor.getColumnIndex(COLUMN_MONEY)));
                Record.setType(cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE)));
                recordLinkedList.add(Record);
                Log.d("date", Record.getDate());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recordLinkedList;
    }

    public List<Record> recordListByCategories(int categoriesIndex){
        List<Record> categoriesList = new LinkedList<>();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE "+ COLUMN_TYPE + " = '" + categoriesIndex + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Record Record;

        if (cursor.moveToFirst()) {
            do {
                Record = new Record();

                Record.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                Record.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                Record.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                Record.setMoney(cursor.getFloat(cursor.getColumnIndex(COLUMN_MONEY)));
                Record.setType(cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE)));
                categoriesList.add(Record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoriesList;
    }

    public List<Record> recordListByCategories(){
        return recordListByCategories(1);
    }

    public Cursor getExpenseData() {
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.close();
        db.close();

        return cursor;
    }

    public List<Double> getSum(String currentDateString){
        List<Double> expense = new ArrayList<Double>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "+ COLUMN_DATE + " = '" + currentDateString + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments
        int length = cursor.getCount();
        Log.d("expense_length", Double.toString(length));

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                double data = cursor.getDouble(cursor.getColumnIndex(COLUMN_MONEY));
                Log.d("expense_data", Double.toString(data));
                int boo = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOLEAN));
                if(boo != 0){
                    expense.add(data);
                }else {
                    expense.add(0-data);
                }
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        return expense;
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
            receivedRecord.setMoney(cursor.getFloat(cursor.getColumnIndex(COLUMN_MONEY)));
            receivedRecord.setType(cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE)));
        }
        return receivedRecord;

    }

    public void deleteRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
    }

    public SparseArray<Float> loadPeiChartData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int categoryIndex = cursor.getColumnIndex(NewRecordDBHelper.COLUMN_TYPE);
        //int dateIndex = cursor.getColumnIndex(ExpensesContract.ExpensesEntry.COLUMN_DATE);
        int moneyIndex = cursor.getColumnIndex(NewRecordDBHelper.COLUMN_MONEY);

        cursor.moveToPosition(-1);
        SparseArray<Float> sumByCategory = new SparseArray<>();
        float sumOfAll =0f;
        while (cursor.moveToNext()) {
            float money = cursor.getFloat(moneyIndex);
            sumOfAll+=money;
        }
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            int category = cursor.getInt(categoryIndex);
            float money = cursor.getFloat(moneyIndex);
            float old = sumByCategory.get(category, 0f);
            Log.d("sumOfAll", Float.toString(sumOfAll));
            sumByCategory.append(category, (old+money));

        }
        return sumByCategory;
    }

    public  List<Double>  getDataByMonth(String month){
        List<Double> expense = new ArrayList<Double>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE strftime('%m', "+ COLUMN_DATE + ") = '" + month + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("selectQuery", selectQuery);
        Log.d("month" , month);
        int i =cursor.getCount();
        if (cursor.moveToFirst()) {
            do {
                double data = cursor.getDouble(cursor.getColumnIndex(COLUMN_MONEY));
                Log.d("expense_data", Double.toString(data));
                int boo = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOLEAN));
                if(boo != 0){
                    expense.add(data);
                }else {
                    expense.add(0-data);
                }
            } while (cursor.moveToNext());
        }
        Log.d("debug sqlite", Integer.toString(i));
        cursor.close();
        db.close();

        return expense;
    }

}
