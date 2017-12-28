//*********************************
// MainActivity.java
//*********************************
// Code created and maintained by:
// Ryan Tinman
//*********************************

package com.ryantryanb.coursework2_6048.dicegame.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.opengl.GLSurfaceView;

import com.ryantryanb.coursework2_6048.dicegame.R;
import com.ryantryanb.coursework2_6048.dicegame.opengl.MyOpenGLRenderer;

//************************
// Activity for main menu
//************************
public class MainActivity extends AppCompatActivity
{
    private GLSurfaceView glView;
    private Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);             // Setting content view for layout XML

        glView = findViewById(R.id.surfaceView);            // Setting GLSurfaceView to the surfaceView in XML layout
        glView.setRenderer(new MyOpenGLRenderer(this));     // Setting custom OpenGL renderer

        startGame = findViewById(R.id.btStartGame);         // Setting startGame to "Start Game" button in XML layout
        onClickStartGame();
    }

    //*******************************************
    // Called when activity is paused (minimised)
    //*******************************************
    @Override
    protected void onPause()
    {
        super.onPause();
        glView.onPause();
    }

    // Call back after onPause()
    //********************************************
    // Called when activity is resumed (maximised)
    //********************************************
    @Override
    protected void onResume()
    {
        super.onResume();
        glView.onResume();
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
