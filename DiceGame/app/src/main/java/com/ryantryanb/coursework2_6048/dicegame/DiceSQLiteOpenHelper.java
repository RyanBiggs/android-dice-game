package com.ryantryanb.coursework2_6048.dicegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ryantryanb.coursework2_6048.dicegame.model.Scores;

import java.util.LinkedList;
import java.util.List;

/**
 * SQLite Database Helper Class
 */

public class DiceSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ScoreBoard";

    public DiceSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("CREATE TABLE IF NOT EXISTS scores (player TEXT, score INT)");    // if it crashes get rid of if not exists
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("DROP TABLE IF EXISTS scores");
    }

    public List<Scores> getScores() {
        SQLiteDatabase myDB = this.getReadableDatabase();

        List<Scores> scores = new LinkedList<Scores>();

        Cursor c = myDB.rawQuery("SELECT * FROM scores", null);

        while (c.moveToNext()) {
            scores.add(new Scores(c.getString(0), c.getInt(1)));
        }

        return scores;
    }

    public void insertScores(Scores scores) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("player", scores.getPlayer());
        cv.put("score", scores.getScore());

        myDB.insert("scores", null, cv);
        myDB.close();
    }


}
