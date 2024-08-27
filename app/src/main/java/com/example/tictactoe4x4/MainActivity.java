package com.example.tictactoe4x4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private TextView playerOneScore, playerTwoScore, playerStatus;
    private final Button[] buttons =new Button[16];
    private Button resetGame;

    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;
    // p1 => 0;
    // p2 => 1
    // empty => 2

    int[] gameState = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {
            {0,1,2,3}, {4,5,6,7}, {8,9,10,11},{12,13,14,15}, // rows
            {0,4,8,12}, {1,5,9,13}, {2,6,10,14},{3,7,11,15}, // columns
            {0,5,10,15}, {3,6,9,12} // cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for(int i=0; i<buttons.length; i++)
        {
            String buttonID = "btn_"+i;
            int resourceID = getResources().getIdentifier(buttonID, "id" , getPackageName());
            buttons[i] = findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScoreCount =0;
        playerTwoScoreCount =0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals(""))
        {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        String btn_new = buttonID.substring(4);// Button number
        // int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1));
        int gameStatePointer = Integer.parseInt(btn_new);


        if (activePlayer)
        {
            ((Button) v).setText("0");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }
        else
        {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;

        if (checkWiner())
        {
            if (activePlayer)
            {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Player One Won!!!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
            else
            {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Player Two Won!!!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }
        else if (rountCount == 16)
        {
            playAgain();
            Toast.makeText(this,"Game Draw!!! Play Again.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            activePlayer = !activePlayer;
        }
        if(playerOneScoreCount > playerTwoScoreCount)
        {
            playerStatus.setText("Player One is Wining");
        }
        else if(playerTwoScoreCount > playerOneScoreCount)
        {
            playerStatus.setText("Player Two is Wining");
        }
        else
        {
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount=0;
                playerTwoScoreCount=0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWiner()
    {
        boolean winerResult = false;

        for (int [] winningPosion : winningPositions)
        {
            if(gameState[winningPosion[0]] == gameState[winningPosion[1]] &&
                    gameState[winningPosion[1]] == gameState[winningPosion[2]] &&
                    gameState[winningPosion[2]] == gameState[winningPosion[3]] &&
                    gameState[winningPosion[0]] != 2)
            {
                winerResult = true;
            }

        }
        return  winerResult;

    }

    public void updatePlayerScore()
    {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain()
    {
        rountCount = 0;
        activePlayer = true;

        for (int i=0; i<buttons.length; i++)
        {
            gameState[i] = 2;
            buttons[i].setText("");
        }

    }
}