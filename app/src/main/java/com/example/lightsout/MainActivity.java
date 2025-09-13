package com.example.lightsout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int GRID_SIZE = 3;
    private GridLayout grid;
    private boolean[][] cellState;
    private TextView scoreTV;

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button current = (Button) view;
            for (int i = 0; i < grid.getChildCount(); i++) {
                Button gridButton = (Button) grid.getChildAt(i);

                if(current == gridButton) {
                    int row = i / GRID_SIZE;
                    int col = i % GRID_SIZE;

                    cellState[row][col] = !cellState[row][col];
                }
            }
            recolor();
            updateScore();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cellState = new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}};

        setContentView(R.layout.activity_main);
        grid = findViewById(R.id.light_grid);
        scoreTV = findViewById(R.id.score);
        randomize();
        updateScore();
        recolor();

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(view -> resetGame());
        Button randomizeButton = findViewById(R.id.randomizeButton);
        randomizeButton.setOnClickListener(view -> {
            randomize();
            recolor();
        });

        for (int i = 0; i < grid.getChildCount(); i++) {
            Button gridButton = (Button) grid.getChildAt(i);
            gridButton.setOnClickListener(buttonListener);
        }
    }

    public void recolor(){
        for (int i = 0; i < grid.getChildCount(); i++) {
            Button gridButton = (Button) grid.getChildAt(i);

            // Find the button's row and col
            int row = i / GRID_SIZE;
            int col = i % GRID_SIZE;

            if (cellState[row][col]) {
                gridButton.setBackgroundColor(getColor(R.color.coral)); // lights on
            } else {
                gridButton.setBackgroundColor(getColor(R.color.black)); // lights off
            }
        }
    }

    public void randomize(){
        Random random = new Random();
        for(int i =0; i< GRID_SIZE; i++){
            for(int j =0; j< GRID_SIZE; j++){
                cellState[i][j] = random.nextBoolean();
            }
        }
        updateScore();
    }

    public int countLightsOn(){
        int count = 0;
        for(int i = 0; i < GRID_SIZE; i++){
            for(int j = 0; j < GRID_SIZE; j++){
                if(cellState[i][j]){
                    count++;
                }
            }
        }
        return count;
    }

    private void updateScore(){
        int count = countLightsOn();
        scoreTV.setText(getString(R.string.score, count));
    }

    private void resetGame() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                cellState[i][j] = false;
            }
        }
        recolor();
        updateScore();
    }
}