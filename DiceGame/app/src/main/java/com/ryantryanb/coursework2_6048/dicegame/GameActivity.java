//*********************************
// GameActivity.java
//*********************************
// Code created and maintained by:
// Ryan Tinman & Ryan Biggs
//*********************************

package com.ryantryanb.coursework2_6048.dicegame;

import com.ryantryanb.coursework2_6048.dicegame.adapter.CustomListAdapter;
import com.ryantryanb.coursework2_6048.dicegame.model.Scores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity
{
    public static final Random RANDOM = new Random();

    private Button rollDice;
    private Button addPlayer;
    private Button removePlayer;
    private Button clearScores;
    private Button returnToMenu;

    private ImageView diceImage1;
    private ImageView diceImage2;

    private TextView textDisplay;

    private static int player = 1;

    private int currentRoll = 0;
    private int highestRoll = 0;

    private DiceSQLiteOpenHelper sqlHelper;
    private List<Scores> scoresList = new ArrayList<Scores>();
    private ListView listView;
    private CustomListAdapter listAdapter;

    // This int keeps track of the number of players in rotation
    public int numOfPlayers = 2;

    public static int randomDiceValue()
    {
        return RANDOM.nextInt(6) + 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        rollDice = findViewById(R.id.btRollDice);
        addPlayer = findViewById(R.id.btAddPlayer);
        removePlayer = findViewById(R.id.btRemovePlayer);
        clearScores = findViewById(R.id.btClearScores);
        returnToMenu = findViewById(R.id.btReturnMenu);

        diceImage1 = findViewById(R.id.diceImage1);
        diceImage2 = findViewById(R.id.diceImage2);
        textDisplay = findViewById(R.id.textDisplay);
        listView = findViewById(R.id.list);

        onClickRollDice();
        onClickAddPlayer();
        onClickRemovePlayer();
        onClickClearScores();
        onClickReturnMenu();

        sqlHelper = new DiceSQLiteOpenHelper(this);

        // If the list has no entries/players, one is added for each of
        // the default two players.
        if (sqlHelper.getScores().isEmpty())
        {
            sqlHelper.setScores(new Scores("1", 0));
            sqlHelper.setScores(new Scores("2", 0));
        }

        setupList();
        updateList();

        numOfPlayers = sqlHelper.getScores().size();
    }

    protected void onResume()
    {
        super.onResume();

        if (listView != null)
        {
            listView.invalidateViews();
        }
        listAdapter.notifyDataSetChanged();
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

    //***********************************************
    // On click event handler for add player button
    //***********************************************
    private void onClickAddPlayer()
    {
        addPlayer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Stops player from removing a player when there are only two players
                if (numOfPlayers >= 2)
                    removePlayer.setEnabled(true);

                // Allows the player to remove a player if there are more than two players
                else
                    removePlayer.setEnabled(false);

                numOfPlayers++;

                // Adds a new player to the scores list
                sqlHelper.setScores(new Scores(new Integer(numOfPlayers).toString(), 0));

                updateList();
            }
        });
    }

    //***********************************************
    // On click event handler for remove player button
    //***********************************************
    private void onClickRemovePlayer()
    {
        removePlayer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Allows the player to remove a player if there are more than two players
                if (numOfPlayers > 2)
                {
                    removePlayer.setEnabled(true);

                    // Removes the player at the end of the rotation from the game
                    // i.e. the player that was last added.
                    sqlHelper.removePlayer(numOfPlayers);

                    numOfPlayers--;
                }

                // Stops player from removing a player when there are only two players
                else
                {
                    removePlayer.setEnabled(false);

                    // Alerts user that there must be at least 2 players
                    makeToast("Must have at least 2 players!", Toast.LENGTH_LONG);
                }
                updateList();
            }
        });
    }

    //***********************************************
    // On click event handler for high scores button
    //***********************************************
    private void onClickClearScores()
    {
        clearScores.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                clearScores();
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
                // This will close the dice game form, returning to
                // the form the user was previously on.
                finish();
            }
        });
    }

    //*************************************
    // Handles functionality for dice game
    //*************************************
    private void diceGame()
    {
        int dice1;
        int dice2;

        String selectedImage1 = null;
        String selectedImage2 = null;

        dice1 = randomDiceValue();
        dice2 = randomDiceValue();

        // Custom animation method to handle multiple text view animations
        textAnim("rollText");

        textDisplay.setText("Player " + player + " rolls : " + (dice1 + dice2));

        // Disables the roll_dice dice button throughout the rolling process, this is so
        // the user does not press it repeatedly.
        rollDice.setEnabled(false);

        switch (dice1)
        {
            case 1:
            {
                selectedImage1 = "one";
                break;
            }

            case 2:
            {
                selectedImage1 = "two";
                break;
            }

            case 3:
            {
                selectedImage1 = "three";
                break;
            }

            case 4:
            {
                selectedImage1 = "four";
                break;
            }

            case 5:
            {
                selectedImage1 = "five";
                break;
            }

            case 6:
            {
                selectedImage1 = "six";
                break;
            }
        }

        switch (dice2)
        {
            case 1:
            {
                selectedImage2 = "one";
                break;
            }

            case 2:
            {
                selectedImage2 = "two";
                break;
            }

            case 3:
            {
                selectedImage2 = "three";
                break;
            }

            case 4:
            {
                selectedImage2 = "four";
                break;
            }

            case 5:
            {
                selectedImage2 = "five";
                break;
            }

            case 6:
            {
                selectedImage2 = "six";
                break;
            }
        }

        // Custom animation method to handle animations for the dice
        diceAnim(selectedImage1, selectedImage2);

        // Method to handle functionality for a player winning
        gameWin(dice1, dice2);

        // The player variable is incremented if the current player is not the last player
        // in the rotation, if they are, the player variable resets back to 1.
        if (player == numOfPlayers)
            player = 1;
        else
            player++;

        // Roll dice button enabled after rolling process is complete.
        rollDice.setEnabled(true);
    }

    //************************************
    // Handles dice animation and rolling
    //************************************
    private void diceAnim(final String SELECTED_IMAGE_1, final String SELECTED_IMAGE_2)
    {
        // Creating animation for dice 1 and dice 2
        final Animation DICE1_ANIM = AnimationUtils.loadAnimation(GameActivity.this, R.anim.roll_dice);
        final Animation DICE2_ANIM = AnimationUtils.loadAnimation(GameActivity.this, R.anim.roll_dice);

        // Animation listener
        final Animation.AnimationListener ANIMATION_LISTENER = new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                rollDice.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                // Setting strings with the uri of the selected dice face image
                String uri1 = "@drawable/" + SELECTED_IMAGE_1;
                String uri2 = "@drawable/" + SELECTED_IMAGE_2;

                int imageResource1 = getResources().getIdentifier(uri1, null, getPackageName());
                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());

                // Creating drawables for the image resources
                Drawable res1 = getResources().getDrawable(imageResource1, null);
                Drawable res2 = getResources().getDrawable(imageResource2, null);

                // Setting the dice images to the correct drawable
                diceImage1.setImageDrawable(res1);
                diceImage2.setImageDrawable(res2);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        };

        // Setting animation listeners for the two dice
        DICE1_ANIM.setAnimationListener(ANIMATION_LISTENER);
        DICE2_ANIM.setAnimationListener(ANIMATION_LISTENER);

        // Starts the animation for dice 1
        diceImage1.startAnimation(DICE1_ANIM);

        //TODO: Add delay between two dice for more realism

        // Starts the animation for dice 2
        diceImage2.startAnimation(DICE2_ANIM);

        // Re-enables the roll dice button after animation is complete
        rollDice.setEnabled(true);
    }

    //****************************************************
    // Handles functionality for player winning the round
    //****************************************************
    private void gameWin(int dice1, int dice2)
    {
        if (dice1 == dice2)
        {
            // Custom animation method to handle multiple text view animations
            textAnim("winText");

            // Sets the text display to show which player won, and with what score
            textDisplay.setText("Player " + player + " wins with : " + (dice1 + dice2) + "!");

            // If the current roll is greater than the highest scoring roll, then the highest
            // roll is updated and a toast message shown to inform players.
            if ((dice1 + dice2) > currentRoll)
            {
                highestRoll = dice1 + dice2;

                String toast = "Highest Scoring Roll : " + highestRoll + " By Player : " + player;
                makeToast(toast, 5);
            }

            currentRoll = dice1 + dice2;

            // Connecting to DB
            SQLiteDatabase myDB = sqlHelper.getWritableDatabase();

            // Updating DB with new scores
            myDB.execSQL("UPDATE scores SET score = score + 1 WHERE player = " + new Integer(player).toString());

            // Closing DB and updating scores list
            myDB.close();
            updateList();
        }
    }

    //***************************************************
    // Handles functionality for populating the ListView
    //***************************************************
    public void setupList()
    {
        // TODO: Biggs, add comments

        listAdapter = new CustomListAdapter(this, scoresList);
        listView.setAdapter(listAdapter);

        SQLiteDatabase myDB = sqlHelper.getReadableDatabase();

        Cursor cursor = myDB.rawQuery("SELECT * FROM scores", null);

        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                Scores scores = new Scores();
                scores.setPlayer(cursor.getString(0));
                scores.setScore(cursor.getInt(1));

                scoresList.add(scores);
                cursor.moveToNext();
            }
        }
        listAdapter.notifyDataSetChanged();
    }

    //*************************************************
    // Handles functionality for updating the ListView
    //*************************************************
    public void updateList()
    {
        // TODO: Biggs, add comments

        listView = findViewById(R.id.list);
        listAdapter = new CustomListAdapter(this, scoresList);
        listView.setAdapter(listAdapter);

        SQLiteDatabase myDB = sqlHelper.getReadableDatabase();

        Cursor cursor = myDB.rawQuery("SELECT * FROM scores", null);

        if (cursor.moveToFirst())
        {
            this.scoresList.clear();
            while (!cursor.isAfterLast())
            {
                Scores scores = new Scores();

                scores.setPlayer(cursor.getString(0));
                scores.setScore(cursor.getInt(1));

                scoresList.add(scores);
                cursor.moveToNext();
            }
        }
        listAdapter.notifyDataSetChanged();
    }

    //*******************************************
    // Handles functionality for clearing scores
    //*******************************************
    private void clearScores()
    {
        // TODO: Biggs, add comments

        SQLiteDatabase myDB = sqlHelper.getWritableDatabase();

        myDB.execSQL("UPDATE scores SET score = 0");

        myDB.close();

        updateList();

        makeToast("Scores Cleared!", Toast.LENGTH_LONG);
    }

    //************************************************
    // Handles functionality creating toast dialogues
    //************************************************
    private void makeToast(CharSequence text, int duration)
    {
        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    //************************
    // Handles text animation
    //************************
    private void textAnim(String animationName)
    {
        Animation textAnim = null;

        // Switch to select different animations for the text display
        switch (animationName)
        {
            case "rollText":
            {
                // Loads the roll text animation when selected
                textAnim = AnimationUtils.loadAnimation(GameActivity.this, R.anim.roll_text);
                break;
            }

            case "winText":
            {
                // Loads the win text animation when selected
                textAnim = AnimationUtils.loadAnimation(GameActivity.this, R.anim.win_text);
                break;
            }
        }

        // Creating animation listener
        final Animation.AnimationListener ANIMATION_LISTENER = new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                rollDice.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                rollDice.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        };

        //Sets animation listener
        textAnim.setAnimationListener(ANIMATION_LISTENER);

        // Starts the animation
        textDisplay.startAnimation(textAnim);
    }
}