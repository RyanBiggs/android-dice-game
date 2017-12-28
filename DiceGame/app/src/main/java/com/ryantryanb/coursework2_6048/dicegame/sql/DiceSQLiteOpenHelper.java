//*********************************
// DiceSQLiteOpenHelper.java
//*********************************
// Code created and maintained by:
// Ryan Biggs
//*********************************

package com.ryantryanb.coursework2_6048.dicegame.sql;

import com.ryantryanb.coursework2_6048.dicegame.model.Scores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

//*******************************
// SQLite Database Handler Class
//*******************************
public class DiceSQLiteOpenHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ScoreBoard";

    public DiceSQLiteOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB)
    {
        myDB.execSQL("CREATE TABLE IF NOT EXISTS scores (player TEXT, score INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion)
    {
        myDB.execSQL("DROP TABLE IF EXISTS scores");
    }

    //**************************
    // Getter method for scores
    //**************************
    public List<Scores> getScores()
    {
        SQLiteDatabase myDB = this.getReadableDatabase();               // Connect to DB

        List<Scores> scores = new LinkedList<>();                       // TODO

        Cursor c = myDB.rawQuery("SELECT * FROM scores", null);         // TODO

        while (c.moveToNext())                                          // TODO
        {
            scores.add(new Scores(c.getString(0), c.getInt(1)));        // TODO
        }

        return scores;
    }

    //**************************
    // Setter method for scores
    //**************************
    public void setScores(Scores scores)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();       // Connect to DB

        ContentValues cv = new ContentValues();                 // TODO

        cv.put("player", scores.getPlayer());                   // TODO
        cv.put("score", scores.getScore());                     // TODO

        myDB.insert("scores", null, cv);                        // TODO
        myDB.close();                                           // Close DB connection
    }

    //***************************
    // Getter method for players
    //***************************
    public List<Scores> getPlayers()
    {
        SQLiteDatabase myDB = this.getReadableDatabase();               // Connect to DB

        List<Scores> scores = new LinkedList<>();                       // TODO

        Cursor c = myDB.rawQuery("SELECT * FROM scores", null);         // TODO

        while (c.moveToNext())                                          // TODO
        {
            scores.add(new Scores(c.getString(0), c.getInt(1)));        // TODO
        }

        return scores;
    }

    //*******************************************
    // Functionality for the removing of players
    //*******************************************
    public boolean removePlayer(int player)
    {
        // Connecting to DB
        SQLiteDatabase myDB = this.getWritableDatabase();

        // Deleting player from table
        return myDB.delete("scores", "player = " + player, null) > 0;
    }
}
