package com.ryantryanb.coursework2_6048.dicegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HighScoresActivity extends AppCompatActivity
{
    private Button rtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        rtn = findViewById(R.id.btReturnFromScores);

        onClickReturn();
    }

    //******************************************
    // On click event handler for return button
    //******************************************
    private void onClickReturn()
    {
        rtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // This will close the high scores form, returning to the form the user was previously on.
                finish();
            }
        });
    }
}