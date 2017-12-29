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
        myDB.execSQL("CREATE TABLE IF NOT EXISTS scores (player TEXT, score INT)"); //Initial create table statement if the table does not already exist
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion)
    {
        myDB.execSQL("DROP TABLE IF EXISTS scores");                            //On upgrading database, delete the table
    }

    //**************************
    // Getter method for scores
    //**************************
    public List<Scores> getScores()
    {
        SQLiteDatabase myDB = this.getReadableDatabase();               // Create a readable connection to DB

        List<Scores> scores = new LinkedList<>();                       // List of scores from generic Scores

        Cursor c = myDB.rawQuery("SELECT * FROM scores", null);         // Result of query assigned to cursor

        while (c.moveToNext())
        {
            scores.add(new Scores(c.getString(0), c.getInt(1)));        // Rows from cursor constructed into a new score and assigned the list scores
        }

        return scores;
    }

    //**************************
    // Method for inserting rows into the database
    //**************************
    public void setScores(Scores scores)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();       // Connect to DB

        ContentValues cv = new ContentValues();

        cv.put("player", scores.getPlayer());                   // Populate cv variable with the data for a new scores
        cv.put("score", scores.getScore());

        myDB.insert("scores", null, cv);                        // SQLite insert method
        myDB.close();                                           // Close DB connection
    }



    //*******************************************
    // Functionality for the removing of players
    //*******************************************
    public boolean removePlayer(int player)
    {

        SQLiteDatabase myDB = this.getWritableDatabase();            // Connecting to DB


        return myDB.delete("scores", "player = " + player, null) > 0;  // Deleting player from table
    }
}
