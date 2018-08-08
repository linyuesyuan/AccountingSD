package com.example.tku.accountingsd.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.tku.accountingsd.model.Cate;

import java.util.LinkedList;
import java.util.List;

public class cateDBHelper extends SQLiteOpenHelper {

    private static final String database = "Cate.db";
    private static final int version = 3;
    public static final String TABLE_NAME = "Cate";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMAGE_ID = "imageId";

    public cateDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public cateDBHelper(Context context) {
        this(context, database, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_IMAGE_ID + " TEXT NOT NULL)"
        );
    }

    /**
     * create record
     **/
    public void saveNewRecord(Cate cate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, cate.getTitle());
        values.put(COLUMN_IMAGE_ID, cate.getImageId());

        // insert
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public List<Cate> cateList(String filter) {
        String query;
        if (filter.equals("")) {
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        } else {
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + filter;
        }

        List<Cate> cateLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Cate cate;

        if (cursor.moveToFirst()) {
            do {
                cate = new Cate();

                cate.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                cate.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                cate.setImageId(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_ID)));
                cateLinkedList.add(cate);
            } while (cursor.moveToNext());
        }

        return cateLinkedList;
    }
    /**Query only 1 record**/
    public Cate getCate(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Cate receivedCate= new Cate();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedCate.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            receivedCate.setImageId(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_ID)));
        }
        return receivedCate;

    }

    public void deleteRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

}
