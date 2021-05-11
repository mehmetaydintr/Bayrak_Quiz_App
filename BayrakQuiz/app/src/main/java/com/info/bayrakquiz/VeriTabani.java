package com.info.bayrakquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class VeriTabani extends SQLiteOpenHelper {
    public VeriTabani(@Nullable Context context) {
        super(context, "bayrakquiz.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS bayraklar (" +
                "bayrak_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "bayrak_ad TEXT," +
                "bayrak_resim TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS bayraklar");
        onCreate(sqLiteDatabase);
    }
}
