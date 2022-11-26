package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private final int[][] possibleSol = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private enum Status {
        X,
        O,
        TIE,
        NONE
    }

    private Status[] arrayOfState = new Status[9];
    private Status whoPlay = Status.X;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setBackgroundResource(R.drawable.back);

        int childCount = gridLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            arrayOfState[i] = Status.NONE;
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int i = gridLayout.indexOfChild(imageView);
                    Log.i("Click", "[" + i % 3 + "][" + i / 3 + "]");
                    imageView.setImageResource(getImage(whoPlay));

                    Status gameStatus = isGameOver();

                    switch (gameStatus) {
                        case NONE: {
                            switchPlayers();
                            break;
                        }
                        default: {
                            restartGameMessage();
                        }
                    }
                }
            });
        }
    }

    private int getImage(Status status) {
        switch (status) {
            case X:
                return R.drawable.x;
            case O:
                return R.drawable.o;
            default:
                return R.drawable.empty;
        }
    }

    private void switchPlayers() {
        whoPlay = whoPlay == Status.X ? Status.O : Status.X;
    }

    private void restartGameMessage() {

    }

    private void restartGame() {

    }

    private Status isGameOver() {
        for (int i = 0; i < possibleSol.length; i++) {
            if (arrayOfState[possibleSol[i][0]] == arrayOfState[possibleSol[i][1]] &&
                    arrayOfState[possibleSol[i][1]] == arrayOfState[possibleSol[i][2]] &&
                    arrayOfState[possibleSol[i][0]] != Status.NONE) {
                return arrayOfState[possibleSol[i][0]];
            }
        }
        for (int i = 0; i < arrayOfState.length; i++) {
            if (arrayOfState[i] == Status.NONE) {
                return Status.NONE;
            }
        }
        return Status.TIE;
    }
}