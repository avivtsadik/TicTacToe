package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final int[][] SOLUTION_INDEXES = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private final int[] SOLUTION_PICS = {R.drawable.mark7, R.drawable.mark8, R.drawable.mark6, R.drawable.mark3, R.drawable.mark4, R.drawable.mark5, R.drawable.mark1, R.drawable.mark2};
    private final int MAX_MOVES = 9;

    private enum Status {
        X, O, TIE, NONE
    }

    private GridLayout gridLayout;
    private Button playAgainBtn;
    private ImageView messageImageView;
    private ImageView winningLineImageView;
    private Status[] arrayOfState = new Status[9];
    private Status whoPlay = Status.X;
    private int numOfMoves = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        playAgainBtn = findViewById(R.id.playAgain);
        messageImageView = findViewById(R.id.messageImageView);
        winningLineImageView = findViewById(R.id.winningLineImageView);

        gridLayout.setBackgroundResource(R.drawable.back);
        playAgainBtn.setVisibility(View.INVISIBLE);
        setWinningLineImageViewParams();

        int childCount = gridLayout.getChildCount();
        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

        for (int i = 0; i < childCount; i++) {
            arrayOfState[i] = Status.NONE;

            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (playAgainBtn.getVisibility() == View.INVISIBLE) {
                        int i = gridLayout.indexOfChild(imageView);
                        Log.d("Click", "[" + i % 3 + "][" + i / 3 + "] | index: " + i);

                        if (arrayOfState[i] == Status.NONE) {
                            imageView.setImageResource(getPlayerImage(whoPlay));
                            arrayOfState[i] = whoPlay;
                            numOfMoves++;

                            int solutionIndex = getSolutionIndex();

                            if (numOfMoves == MAX_MOVES && solutionIndex == -1) {
                                changeGameMessage(Status.TIE);
                                playAgainBtn.setVisibility(View.VISIBLE);
                            } else if (solutionIndex != -1) {
                                winningLineImageView.setImageResource(SOLUTION_PICS[solutionIndex]);
                                playAgainBtn.setVisibility(View.VISIBLE);
                                changeGameMessage(whoPlay);
                            } else {
                                switchPlayers();
                                changeGameMessage(Status.NONE);
                            }
                        }
                    }
                }
            });
        }

        changeGameMessage(Status.NONE);
    }

    private int getPlayerImage(Status status) {
        switch (status) {
            case X:
                return R.drawable.x;
            case O:
                return R.drawable.o;
            default:
                return R.drawable.empty;
        }
    }

    private int getMessageImage(Status status) {
        switch (status) {
            case X:
                return R.drawable.xwin;
            case O:
                return R.drawable.owin;
            case TIE:
                return R.drawable.nowin;
            case NONE:
                return whoPlay == Status.X ? R.drawable.xplay : R.drawable.oplay;
            default:
                return R.drawable.xwin;
        }
    }

    private void switchPlayers() {
        whoPlay = whoPlay == Status.X ? Status.O : Status.X;
    }

    private void changeGameMessage(Status gameStatus) {
        Log.i("restartGameMessage", gameStatus.toString());
        messageImageView.setImageResource(getMessageImage(gameStatus));
    }

    private void restartGame() {
        arrayOfState = new Status[9];
        whoPlay = Status.X;
        numOfMoves = 0;
        messageImageView.setImageResource(R.drawable.empty);
        winningLineImageView.setImageResource(R.drawable.empty);
        playAgainBtn.setVisibility(View.INVISIBLE);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            arrayOfState[i] = Status.NONE;
            imageView.setImageResource(R.drawable.empty);
        }
    }

    private int getSolutionIndex() {
        for (int i = 0; i < SOLUTION_INDEXES.length; i++) {
            if (arrayOfState[SOLUTION_INDEXES[i][0]] == arrayOfState[SOLUTION_INDEXES[i][1]] && arrayOfState[SOLUTION_INDEXES[i][1]] == arrayOfState[SOLUTION_INDEXES[i][2]] && arrayOfState[SOLUTION_INDEXES[i][0]] != Status.NONE) {
                return i;
            }
        }

        return -1;
    }

    private void setWinningLineImageViewParams() {
        winningLineImageView.setLayoutParams(gridLayout.getLayoutParams());
        winningLineImageView.bringToFront();
    }
}