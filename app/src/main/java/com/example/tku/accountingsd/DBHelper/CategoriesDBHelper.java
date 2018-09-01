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
    private static final String TABLE_NAME = "Categories";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FILE_NAME = "fileName";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_COLOR = "color";
    private static final String COLUMN_BOOLEAN = "expense_income";

    private Context mContext;

    private CategoriesDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
        private static final String CREATE_TABLE_QUERY =
                " CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_FILE_NAME +" TEXT NOT NULL, "+
                        COLUMN_TITLE + " TEXT NOT NULL, "+
                        COLUMN_COLOR + " INTEGER NOT NULL, "+
                        COLUMN_BOOLEAN + " INTEGER NOT NULL)";

        //COLUMN_BOOLEAN + " INTEGER DEFAULT 0

        public static final String DELETE_TABLE_QUERY =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        private static void fillTable(SQLiteDatabase db, Context ctx) {
            ContentValues values = new ContentValues();

            int i =0;

            String[] predefinedTitle = ctx.getResources().getStringArray(R.array.predefined_categories);
            String[] fileName = ctx.getResources().getStringArray(R.array.preset_categories_name);
            int[] type ={0, 0, 0, 0, 0, 0, 0, 1, 1};
            List<Integer> colorArray = new ArrayList<>();
            colorArray.add(1676170361);
            colorArray.add(1676189268);
            colorArray.add(1307109418);
            colorArray.add(1300228159);
            colorArray.add(1297410206);
            colorArray.add(1294657768);
            colorArray.add(1298774504);
            colorArray.add(1297372392);
            colorArray.add(1306028520);

            for (String title : predefinedTitle) {
                values.put(COLUMN_FILE_NAME, fileName[i]);
                Log.d("boolean", Integer.toString(type[i]));
                values.put(COLUMN_BOOLEAN, type[i]);
                values.put(COLUMN_TITLE, title);
                values.put(COLUMN_COLOR, colorArray.get(i));
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

    public List<Categories> categoriesList() {
        String query = "SELECT  * FROM " + TABLE_NAME;


        List<Categories> categoriesLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Categories categories;

        if (cursor.moveToFirst()) {
            do {
                categories = new Categories();

                categories.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                categories.setFileName(cursor.getString(cursor.getColumnIndex(COLUMN_FILE_NAME)));
                categories.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                categories.setType(cursor.getInt(cursor.getColumnIndex(COLUMN_BOOLEAN)));
                categories.setColor(cursor.getInt(cursor.getColumnIndex(COLUMN_COLOR)));
                categoriesLinkedList.add(categories);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

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
        cursor.close();
        db.close();
        return receivedCate;
    }

    public void deleteCategories(int id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
        db.close();

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

    public SparseArray<Integer> loadColor(){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        SparseArray<Integer> colorList = new SparseArray<>();
        if(cursor.moveToFirst()){
            do{
                int categoriesId = (int)cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                int colorIndex = cursor.getInt(cursor.getColumnIndex(COLUMN_COLOR));
                String categories = cursor.getString((cursor.getColumnIndex(COLUMN_TITLE)));
                colorList.append(categoriesId, colorIndex);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return colorList;
    }
    public int passBooleanByCategories(int categoriesIndex){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_ID +"="+ categoriesIndex;
        Cursor cursor = db.rawQuery(query,null);
        int exp_inc = 0;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            exp_inc = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOLEAN));
        }
        cursor.close();
        db.close();
        return exp_inc;
    }

}
