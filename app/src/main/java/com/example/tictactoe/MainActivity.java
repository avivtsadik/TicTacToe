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
        ImageView messageImageView = findViewById(R.id.imageView);

        int childCount = gridLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            arrayOfState[i] = Status.NONE;

            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int i = gridLayout.indexOfChild(imageView);
                    Log.i("Click", "[" + i % 3 + "][" + i / 3 + "]");

                    if(arrayOfState[i] == Status.NONE){
                        imageView.setImageResource(getPlayerImage(whoPlay));
                        arrayOfState[i] = whoPlay;

                        Status gameStatus = isGameOver();
                        if(gameStatus == Status.NONE){
                            switchPlayers();
                        }
                        changeGameMessage(gameStatus,messageImageView);
                    }
                }
            });
        }
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
                return whoPlay == Status.X ? R.drawable.xplay :R.drawable.oplay;
            default:
                return R.drawable.xwin;
        }
    }

    private void switchPlayers() {
        whoPlay = whoPlay == Status.X ? Status.O : Status.X;
    }

    private void changeGameMessage(Status gameStatus,ImageView messageImageView) {
        Log.i("restartGameMessage", gameStatus.toString());
        messageImageView.setImageResource(getMessageImage(gameStatus));
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