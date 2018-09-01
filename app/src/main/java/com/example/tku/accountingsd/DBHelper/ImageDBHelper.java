package com.example.tku.accountingsd.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.ImageData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ImageDBHelper extends SQLiteOpenHelper {

    private static final String database = "Image.db";
    private static final int version = 3;
    private static final String TABLE_NAME = "Image";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_IMAGE_DATA = "image_data";

    private Context mContext;

    public ImageDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ImageDBHelper(Context context) {
        this(context, database, null, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ImageTable.CREATE_TABLE_QUERY);
        // Fill the table with predefined values
        ImageTable.fillTable(db, mContext);
    }

    private static final class ImageTable {
        private static final String CREATE_TABLE_QUERY =
                " CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_IMAGE_DATA + " BLOB) ";

        public static final String DELETE_TABLE_QUERY =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        private static void fillTable(SQLiteDatabase db, Context ctx) {

            ContentValues values = new ContentValues();

            TypedArray imageID = ctx.getResources().obtainTypedArray(R.array.categories_image);
            Bitmap bitmap;
            ImageData categoriesImage =new ImageData();
            for(int i =0; i<imageID.length(); i++){
                bitmap = BitmapFactory.decodeResource(ctx.getResources(), imageID.getResourceId(i,0));
                values.put(COLUMN_IMAGE_DATA,categoriesImage.bitmapToByte(bitmap));
                db.insert(TABLE_NAME,null,values);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public List<ImageData> imageList(){
        List<ImageData> imageDataList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        ImageData imageData;

        if(cursor.moveToFirst()) {
            do{
                cursor.moveToFirst();
                imageData = new ImageData();
                imageData.setImageDataFromByte(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE_DATA)));
                imageData.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                imageDataList.add(imageData);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return imageDataList;
    }

    public byte[] getImage(int imageIndex){
        byte[] image =null;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE _id = '" + imageIndex + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                image = cursor.getBlob((cursor.getColumnIndex(COLUMN_IMAGE_DATA)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return image;
    }
}
