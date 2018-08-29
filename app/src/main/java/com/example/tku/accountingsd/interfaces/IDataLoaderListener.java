package com.example.tku.accountingsd.interfaces;

import android.database.Cursor;
import android.util.SparseArray;

public interface IDataLoaderListener {
    SparseArray<Float> onDataLoaded(int id, Cursor cursor);
}
