package com.ryantryanb.coursework2_6048.dicegame.model;

/**
 * Plain old java object
 *
 * Scores Model
 */

public class Scores {


    private String player;
    private int score;

    public Scores() {
    }

    public Scores(String player, int score) {

        this.player = player;
        this.score = score;

    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
