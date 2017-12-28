//*********************************
// Scores.java
//*********************************
// Code created and maintained by:
// Ryan Biggs
//*********************************

package com.ryantryanb.coursework2_6048.dicegame.model;

public class Scores
{
    private String player;
    private int score;

    //  Initialisation
    public Scores()
    {
    }

    // Constructor
    public Scores(String player, int score)
    {
        this.player = player;
        this.score = score;
    }

    //*******************
    // Getter for player
    //*******************
    public String getPlayer()
    {
        return player;
    }

    //*******************
    // Setter for player
    //*******************
    public void setPlayer(String player)
    {
        this.player = player;
    }

    //******************
    // Getter for score
    //******************
    public int getScore()
    {
        return score;
    }

    //******************
    // Setter for score
    //******************
    public void setScore(int score)
    {
        this.score = score;
    }
}
