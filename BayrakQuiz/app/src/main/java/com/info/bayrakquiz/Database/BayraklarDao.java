package com.info.bayrakquiz.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.info.bayrakquiz.Database.VeriTabani;
import com.info.bayrakquiz.Objects.Bayraklar;

import java.util.ArrayList;

public class BayraklarDao {

    public ArrayList<Bayraklar> rastgele5Getir(VeriTabani vt){
        ArrayList<Bayraklar> bayraklarArrayList = new ArrayList<>();

        SQLiteDatabase database = vt.getWritableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM bayraklar ORDER BY RANDOM() LIMIT 5", null);

        while (c.moveToNext()){
            Bayraklar b = new Bayraklar(c.getInt(c.getColumnIndex("bayrak_id")),
                    c.getString(c.getColumnIndex("bayrak_ad")),
                    c.getString(c.getColumnIndex("bayrak_resim")));
            bayraklarArrayList.add(b);
        }

        return bayraklarArrayList;
    }

    public ArrayList<Bayraklar> rastgele3YanlisSecenekGetir(VeriTabani vt, int bayrak_id){
        ArrayList<Bayraklar> bayraklarArrayList = new ArrayList<>();

        SQLiteDatabase database = vt.getWritableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM bayraklar WHERE bayrak_id != "+bayrak_id+" ORDER BY RANDOM() LIMIT 3", null);

        while (c.moveToNext()){
            Bayraklar b = new Bayraklar(c.getInt(c.getColumnIndex("bayrak_id")),
                    c.getString(c.getColumnIndex("bayrak_ad")),
                    c.getString(c.getColumnIndex("bayrak_resim")));
            bayraklarArrayList.add(b);
        }

        return bayraklarArrayList;
    }
}
