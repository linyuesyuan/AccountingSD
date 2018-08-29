package com.example.tku.accountingsd.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Categories;
import com.example.tku.accountingsd.model.ImageData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CategoriesDBHelper extends SQLiteOpenHelper {

    private static final String database = "Categories.db";
    private static final int version = 3;
    public static final String TABLE_NAME = "Categories";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    private static final String COLUMN_IMAGE_DATA = "image_data";

    String TAG = "mama";

    private Context mContext;

    public CategoriesDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CategoriesDBHelper(Context context) {
        this(context, database, null, version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoriesTable.CREATE_TABLE_QUERY);
        // Fill the table with predefined values
        CategoriesTable.fillTable(db, mContext);

    }


    private static final class CategoriesTable{
        public static final String CREATE_TABLE_QUERY =
                " CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_TITLE + " TEXT NOT NULL, "+
                        COLUMN_IMAGE_DATA + " BLOB)";

        public static final String DELETE_TABLE_QUERY =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public static void fillTable(SQLiteDatabase db, Context ctx) {


            ContentValues values = new ContentValues();


            TypedArray imageID = ctx.getResources().obtainTypedArray(R.array.categories_image);
            Bitmap bitmap;
            ImageData categoriesImage =new ImageData();

            int i =0;

            String[] predefinedTitle = ctx.getResources().getStringArray(R.array.predefined_categories);

            for (String title : predefinedTitle) {

                bitmap = BitmapFactory.decodeResource(ctx.getResources(), imageID.getResourceId(i,0));
                values.put(COLUMN_IMAGE_DATA,categoriesImage.bitmapToByte(bitmap));
                values.put(COLUMN_TITLE, title);
                db.insert(TABLE_NAME,null,values);
                i++;
            }


        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    /**
     * create record
     **/
    public void saveNewCategories(Categories categories) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, categories.getTitle());

        // insert
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Categories> categoriesList(String filter) {
        String query;
        if (filter.equals("")) {
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        } else {
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + filter;
        }

        List<Categories> categoriesLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Categories categories;

        if (cursor.moveToFirst()) {
            do {
                categories = new Categories();

                categories.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                categories.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                categories.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE_DATA)));
                categoriesLinkedList.add(categories);
            } while (cursor.moveToNext());
        }

        return categoriesLinkedList;
    }
    /**Query only 1 record**/
    public Categories getCategories(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Categories receivedCate= new Categories();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedCate.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        }
        return receivedCate;

    }

    public void deleteCategories(int id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    public SparseArray<String> getAllTitle(){
        SparseArray<String> titleIndexArray= new SparseArray<>();
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int categoriesID =(int)cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String categoriesTitle = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                list.add(cursor.getString(1));
                titleIndexArray.append(categoriesID, categoriesTitle);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return titleIndexArray;
    }

    public byte[] getImage(int categoriesIndex){
        byte[] image =null;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE _id = '" + categoriesIndex + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                image = cursor.getBlob((cursor.getColumnIndex(COLUMN_IMAGE_DATA)));
            } while (cursor.moveToNext());
        }

        if(image == null){
            Log.d(TAG, "image are null");
        }

        cursor.close();
        db.close();
        return image;
    }

    public SparseArray<String> loadCategoriesTitle(){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        SparseArray<String> categoriesTitle = new SparseArray<>();
        if(cursor.moveToFirst()){
            do{
                int categoriesId = (int)cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String categories = cursor.getString((cursor.getColumnIndex(COLUMN_TITLE)));
                categoriesTitle.append(categoriesId, categories);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoriesTitle;

    }

}
