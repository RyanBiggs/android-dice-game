package com.ryantryanb.coursework2_6048.dicegame;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity
{
    private Button rollDice;
    private Button highScores;
    private Button returnToMenu;

    private ImageView diceImage1;
    private ImageView diceImage2;

    private TextView textDisplay;

    private static int player = 1;

    // TODO
    //************************
    // SET UP FOR HIGH SCORES
    //************************
    private TextView highScoresTest1;
    private TextView highScoresTest2;
    private TextView highestRollText;
    private int totalWinsPlayer1;
    private int TotalWinsPlayer2;
    private int currentRoll = 0;
    private int highestRoll = 0;
    //************************


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        highScores = findViewById(R.id.btHighScores);
        returnToMenu = findViewById(R.id.btReturnMenu);
        rollDice = findViewById(R.id.btRollDice);

        diceImage1 = findViewById(R.id.diceImage1);
        diceImage2 = findViewById(R.id.diceImage2);
        textDisplay = findViewById(R.id.textDisplay);

        // TODO
        //************************
        // SET UP FOR HIGH SCORES
        //************************
        highScoresTest1 = findViewById(R.id.highScoresTest1);
        highScoresTest2 = findViewById(R.id.highScoresTest2);
        highestRollText = findViewById(R.id.highestRollText);
        //************************

        onClickHighScores();
        onClickReturnMenu();
        onClickRollDice();
    }

    //***********************************************
    // On click event handler for high scores button
    //***********************************************
    private void onClickHighScores()
    {
        highScores.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                launchHighScoresActivity();
            }
        });
    }

    //**************************************************
    // On click event handler for return to menu button
    //**************************************************
    private void onClickReturnMenu()
    {
        returnToMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // This will close the dice game form, returning to the form the user was previously on.
                finish();
            }
        });
    }

    //*********************************************
    // On click event handler for roll dice button
    //*********************************************
    private void onClickRollDice()
    {
        rollDice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                diceGame();
            }
        });
    }

    //*******************************************
    // Intent for changing to HighScoresActivity
    //*******************************************
    private void launchHighScoresActivity()
    {
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
    }

    //**********************************
    // Main functionality for dice game
    //**********************************
    private void diceGame()
    {
        //TODO: Put diceGame on seperate thread so it can be paused independently of the UI thread.
        Random rand = new Random();

        int dice1;
        int dice2;

        String selectedImage1 = null;
        String selectedImage2 = null;

        textDisplay.setText("Player " + player + " rolls :");

        rollDice.setEnabled(false);
        //TODO: Wait for 750 milliseconds

        dice1 = rand.nextInt(5) + 1;
        dice2 = rand.nextInt(5) + 1;

        switch (dice1)
        {
            case 1: {
                selectedImage1 = "one";
                break;
            }

            case 2: {
                selectedImage1 = "two";
                break;
            }

            case 3: {
                selectedImage1 = "three";
                break;
            }

            case 4: {
                selectedImage1 = "four";
                break;
            }

            case 5: {
                selectedImage1 = "five";
                break;
            }

            case 6: {
                selectedImage1 = "six";
                break;
            }
        }

        //TODO: Wait for 150 milliseconds

        switch (dice2)
        {
            case 1: {
                selectedImage2 = "one";
                break;
            }

            case 2: {
                selectedImage2 = "two";
                break;
            }

            case 3: {
                selectedImage2 = "three";
                break;
            }

            case 4: {
                selectedImage2 = "four";
                break;
            }

            case 5: {
                selectedImage2 = "five";
                break;
            }

            case 6: {
                selectedImage2 = "six";
                break;
            }
        }

        //**********************
        // Setting dice 1 image
        //**********************
        String uri1 = "@drawable/" + selectedImage1;

        int imageResource1 = getResources().getIdentifier(uri1, null, getPackageName());
        Drawable res1 = getResources().getDrawable(imageResource1, null);
        diceImage1.setImageDrawable(res1);

        //******************
        // Animating dice 1
        //******************
        AnimatorSet dice1Anim = new AnimatorSet();

        dice1Anim.play(ObjectAnimator.ofFloat(diceImage1, "scaleX", 0, 1))
                .with(ObjectAnimator.ofFloat(diceImage1, "scaleY", 0, 1));

        dice1Anim.setStartDelay(0);
        dice1Anim.setDuration(500);
        dice1Anim.start();

        //**********************
        // Setting dice 2 image
        //**********************
        String uri2 = "@drawable/" + selectedImage2;

        int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
        Drawable res2 = getResources().getDrawable(imageResource2, null);
        diceImage2.setImageDrawable(res2);

        //******************
        // Animating dice 2
        //******************
        AnimatorSet dice2Anim = new AnimatorSet();

        dice2Anim.play(ObjectAnimator.ofFloat(diceImage2, "scaleX", 0, 1))
                .with(ObjectAnimator.ofFloat(diceImage2, "scaleY", 0, 1));

        dice2Anim.setStartDelay(0);
        dice2Anim.setDuration(500);
        dice2Anim.start();
        //******************

        if (dice1 == dice2)
        {
            textDisplay.setText("Player " + player + " wins !");

            //************************
            // Animating text display
            //************************
            AnimatorSet flash = new AnimatorSet();

            flash.play(ObjectAnimator.ofFloat(textDisplay, "scaleX", 0, 1))
                    .with(ObjectAnimator.ofFloat(textDisplay, "scaleY", 0, 1));

            flash.setStartDelay(0);
            flash.setDuration(500);
            flash.start();
            //************************

            //TODO: Wait for 150 milliseconds

            // TODO
            //************************
            // SET UP FOR HIGH SCORES
            //************************
            if ((dice1 + dice2) > currentRoll)
            {
                highestRoll = dice1 + dice2;
            }

            currentRoll = dice1 + dice2;

            if (player == 1)
            {
                totalWinsPlayer1++;

                highScoresTest1.setText("Total Wins Player 1: " + totalWinsPlayer1);

                if (highestRoll >= currentRoll)
                {
                    highestRollText.setText("Highest Roll: " + highestRoll + ". By Player 1");
                }
            }
            else {
                TotalWinsPlayer2++;

                highScoresTest2.setText("Total Wins Player 2: " + TotalWinsPlayer2);

                if (highestRoll >= currentRoll)
                {
                    highestRollText.setText("Highest Roll: " + highestRoll + ". By Player 2");
                }
            }
            //************************
        }

        if (player == 1)
        {
            player = 2;
        }
        else
        {
            player = 1;
        }

        rollDice.setEnabled(true);
    }
}