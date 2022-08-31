package com.mad.gamer.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mad.gamer.model.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Gamer";
    private static final String TABLE_NAME = "HiScores";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_SCORE + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public boolean createScore(Model model) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, model.getName());
        values.put(KEY_SCORE, model.getScore());
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public List<Model> readScores() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Model> modelList = new ArrayList<Model>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " order by " + KEY_SCORE + " DESC limit 5";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model model = new Model();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setName(cursor.getString(1));
                model.setScore(Integer.parseInt(cursor.getString(2)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }
        return modelList;
    }

    public boolean canCreateScore(int score) {
        List<Model> modelList = readScores();
        Collections.reverse(modelList);
        boolean result = false;
        for (Model model : modelList){
            if (score >= model.getScore() ){
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean deleteScore(long _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, KEY_ID + "=" + _id, null) > 0;
    }
}
