//*********************************
// MainActivity.java
//*********************************
// Code created and maintained by:
// Ryan Tinman
//*********************************

package com.ryantryanb.coursework2_6048.dicegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame = findViewById(R.id.btStartGame);

        onClickStartGame();
    }

    //**********************************************
    // On click event handler for start game button
    //**********************************************
    private void onClickStartGame()
    {
        startGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                launchGameActivity();
            }
        });
    }

    //*************************************
    // Intent for changing to GameActivity
    //*************************************
    private void launchGameActivity()
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
