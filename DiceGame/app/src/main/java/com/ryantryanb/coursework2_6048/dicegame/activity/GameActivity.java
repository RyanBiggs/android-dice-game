//*********************************
// GameActivity.java
//*********************************
// Code created and maintained by:
// Ryan Tinman & Ryan Biggs
//*********************************

package com.ryantryanb.coursework2_6048.dicegame.activity;

import com.ryantryanb.coursework2_6048.dicegame.sql.DiceSQLiteOpenHelper;
import com.ryantryanb.coursework2_6048.dicegame.R;
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

//*****************************
// Activity for main dice game
//*****************************
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

    private static byte player = 0;

    private byte currentRoll = 0;
    private byte highestRoll = 0;
    private byte maxPlayers = 10;

    private DiceSQLiteOpenHelper sqlHelper;
    private List<Scores> scoresList = new ArrayList<>();
    private ListView listView;
    private CustomListAdapter listAdapter;

    public byte numOfPlayers = 2;        // Keeps track of the number of players in rotation

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

        if (sqlHelper.getScores().isEmpty())            // If scores table is empty (i.e on fresh app install)
        {
            sqlHelper.setScores(new Scores("1", 0));    // Add default player 1
            sqlHelper.setScores(new Scores("2", 0));    // And player 2
        }

        setupList();
        updateList();

        numOfPlayers = (byte) sqlHelper.getScores().size();
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
                if (numOfPlayers >= 2)                  // If there are 2 or more players:
                    removePlayer.setEnabled(true);      // Re-enable remove player button so they can be removed later

                if (numOfPlayers <= maxPlayers - 1)     // If the maximum amount of players hasn't been reached:
                {
                    numOfPlayers++;                     // Increment numOfPlayers variable

                    sqlHelper.setScores(new Scores(Integer.valueOf(numOfPlayers).toString(), 0));   // Add a new player to the scores list
                }

                else                                                                 // Otherwise: (If maximum player count has been reached)
                {
                    addPlayer.setEnabled(false);                                     // Disable remove player button

                    makeToast(getString(R.string.tst_max_player_reached), Toast.LENGTH_LONG);   // Alert user that maximum player count has been reached
                }

                updateList();       // Update score list
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
                if (numOfPlayers <= maxPlayers)             // If the number of players is less than or equal to the maximum allowed amount:
                    addPlayer.setEnabled(true);             // Re-enable add player button so they can be re-added later

                if (numOfPlayers > 2)                       // If there are more than 2 players:
                {
                    removePlayer.setEnabled(true);          // Enable remove player button

                    sqlHelper.removePlayer(numOfPlayers);   // Removes the player at the end of the rotation from the game
                                                            // i.e. the player that was last added.
                    numOfPlayers--;
                }

                else                                                                    // Otherwise: (If there are only two players in the rotation)
                {
                    removePlayer.setEnabled(false);                                     // Disable remove player button

                    makeToast(getString(R.string.tst_must_have_2_players), Toast.LENGTH_LONG);      // Alerts user that there must be at least 2 players
                }

                updateList();           // Update score list
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
                finish();       // This will close the dice game form, returning to the form the user was previously on.
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


        if (player == numOfPlayers)     // If current player is last in rotation, go back to player 1
            player = 1;

        else                            // Otherwise: (If current player is not last in rotation, go to next player)
            player++;

        if (player > numOfPlayers)      // Error checking
        {
            player = 1;
        }

        textAnim("rollText");           // Custom animation method to handle multiple text view animations

        textDisplay.setText("Player " + player + " rolls : " + (dice1 + dice2));

        rollDice.setEnabled(false);         // Disables the roll dice button throughout the rolling process, so
                                            // the user does not press it repeatedly.
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


        diceAnim(selectedImage1, selectedImage2);   // Custom animation method to handle animations for the dice

        gameWin(dice1, dice2);                      // Method to handle functionality for a player winning

        rollDice.setEnabled(true);                  // Roll dice button enabled after rolling process is complete.
    }

    //************************************
    // Handles dice animation and rolling
    //************************************
    private void diceAnim(final String SELECTED_IMAGE_1, final String SELECTED_IMAGE_2)
    {
        final Animation DICE1_ANIM = AnimationUtils.loadAnimation(GameActivity.this, R.anim.roll_dice);     // Loading animation for dice 1
        final Animation DICE2_ANIM = AnimationUtils.loadAnimation(GameActivity.this, R.anim.roll_dice);     // Loading animation for dice 2

        final Animation.AnimationListener ANIMATION_LISTENER = new Animation.AnimationListener()            // Animation listener
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                rollDice.setEnabled(false);     // Disable roll dice button for duration of animation
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                String uri1 = "@drawable/" + SELECTED_IMAGE_1;      // Setting URI of selected face image for dice 1
                String uri2 = "@drawable/" + SELECTED_IMAGE_2;      // Setting URI of selected face image for dice 2

                int imageResource1 = getResources().getIdentifier(uri1, null, getPackageName());
                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());

                Drawable res1 = getResources().getDrawable(imageResource1, null);       // Creating drawable for dice 1 image
                Drawable res2 = getResources().getDrawable(imageResource2, null);       // Creating drawable for dice 2 image

                diceImage1.setImageDrawable(res1);      // Setting dice 1 image to correct drawable
                diceImage2.setImageDrawable(res2);      // Setting dice 2 image to correct drawable
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
        };

        DICE1_ANIM.setAnimationListener(ANIMATION_LISTENER);    // Setting animation listener for dice 1
        DICE2_ANIM.setAnimationListener(ANIMATION_LISTENER);    // Setting animation listener for dice 2

        diceImage1.startAnimation(DICE1_ANIM);                  // Starts the animation for dice 1
        diceImage2.startAnimation(DICE2_ANIM);                  // Starts the animation for dice 2

        rollDice.setEnabled(true);          // Re-enables the roll dice button after animation is complete
    }

    //****************************************************
    // Handles functionality for player winning the round
    //****************************************************
    private void gameWin(int dice1, int dice2)
    {
        if (dice1 == dice2)
        {
            textAnim("winText");                                                                    // Play text animation for winning a game

            textDisplay.setText("Player " + player + " wins with : " + (dice1 + dice2) + "!");      // Sets the text display to show which player won, and with what score


            if ((dice1 + dice2) > currentRoll)                                                      // If the current roll is greater than the highest scoring roll...
            {
                highestRoll = (byte) (dice1 + dice2);                                               // Then the highest roll is updated

                makeToast(getString(R.string.tst_highest_roll_part_1)                               // and a toast message shown to inform players.
                        + " " + highestRoll
                        + " " + (getString(R.string.tst_highest_roll_part_2)
                        + " " + player), Toast.LENGTH_LONG);
            }

            currentRoll = (byte) (dice1 + dice2);

            SQLiteDatabase myDB = sqlHelper.getWritableDatabase();                                                      // Open DB Connection

            myDB.execSQL("UPDATE scores SET score = score + 1 WHERE player = " + Integer.valueOf(player).toString());   // Update DB with new scores

            myDB.close();       // Close DB
            updateList();       // Update scores list
        }
    }

    //***************************************************
    // Handles functionality for populating the ListView
    //***************************************************
    public void setupList()
    {
        listAdapter = new CustomListAdapter(this, scoresList);          // Assign the List Adapter
        listView.setAdapter(listAdapter);

        SQLiteDatabase myDB = sqlHelper.getReadableDatabase();          // Open DB Connection

        Cursor cursor = myDB.rawQuery("SELECT * FROM scores", null);    // Assign a Cursor using an SQL Query

        if (cursor.moveToFirst())                                       // Move the Cursor to the first player
        {
            while (!cursor.isAfterLast())                               // If the Cursor is before the last player, run the loop again
            {
                Scores scores = new Scores();                           // The loop fetches the player number and score for each player and adds them to the list view
                scores.setPlayer(cursor.getString(0));
                scores.setScore(cursor.getInt(1));

                scoresList.add(scores);
                cursor.moveToNext();
            }
        }

        listAdapter.notifyDataSetChanged();                             // Notify the ListView data has changed and needs to be refreshed

        cursor.close();                                                 // Close Cursor
    }

    //*************************************************
    // Handles functionality for updating the ListView
    //*************************************************
    public void updateList()
    {
        listAdapter = new CustomListAdapter(this, scoresList);          // Assign the List Adapter
        listView.setAdapter(listAdapter);

        SQLiteDatabase myDB = sqlHelper.getReadableDatabase();          // Open DB Connection

        Cursor cursor = myDB.rawQuery("SELECT * FROM scores", null);    // Assign a Cursor using an SQL Query

        if (cursor.moveToFirst())                                       // Move the Cursor to the first player
        {
            this.scoresList.clear();                                    // Clear the ListView in order to repopulate with the updated data

            while (!cursor.isAfterLast())                               // If the Cursor is before the last player, run the loop again
            {
                Scores scores = new Scores();                           // The loop fetches the player number and score for each player and adds them to the list view


                scores.setPlayer(cursor.getString(0));
                scores.setScore(cursor.getInt(1));

                scoresList.add(scores);
                cursor.moveToNext();
            }
        }

        listAdapter.notifyDataSetChanged();                             // Notify the ListView data has changed and needs to be refreshed

        cursor.close();                                                 // Close Cursor
    }

    //*******************************************
    // Handles functionality for clearing scores
    //*******************************************
    private void clearScores()
    {
        SQLiteDatabase myDB = sqlHelper.getWritableDatabase();                  // Connect to DB

        myDB.execSQL("UPDATE scores SET score = 0");                            // Execute raw SQL query

        myDB.close();                                                           // Close DB connection

        updateList();                                                           // Update score list

        makeToast(getString(R.string.tst_scores_cleared), Toast.LENGTH_SHORT);   // Confirmation message
    }

    //************************************************
    // Handles functionality creating toast dialogues
    //************************************************
    private void makeToast(CharSequence text, int duration)
    {
        Context context = getApplicationContext();                  // Set context

        Toast toast = Toast.makeText(context, text, duration);      // Create toast with given parameters
        toast.show();                                               // Display toast
    }


    //************************
    // Handles text animation
    //************************
    private void textAnim(String animationName)
    {
        Animation textAnim = null;

        switch (animationName)      // Switch to select different animations for the text display
        {
            case "rollText":
            {
                textAnim = AnimationUtils.loadAnimation(GameActivity.this, R.anim.roll_text);       // Loads the roll text animation
                break;
            }

            case "winText":
            {
                textAnim = AnimationUtils.loadAnimation(GameActivity.this, R.anim.win_text);        // Loads the win text animation
                break;
            }
        }

        final Animation.AnimationListener ANIMATION_LISTENER = new Animation.AnimationListener()    // Creating animation listener
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

                rollDice.setEnabled(false);     // Disable roll dice button for duration of animation
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                rollDice.setEnabled(true);      // enable roll dice button once animation is over
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
        };

        if (textAnim != null)                                   // If textAnim is not null
            textAnim.setAnimationListener(ANIMATION_LISTENER);  // Set animation listener

        textDisplay.startAnimation(textAnim);                   // Starts animation
    }
}