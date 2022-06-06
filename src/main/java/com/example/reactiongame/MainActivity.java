package com.example.reactiongame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import java.util.Random;

/*
this is the main menu, it will have instructions and a button to start the game
 */

public class MainActivity extends AppCompatActivity {

    Button StartGamebtn;
    TextView Instructions;
    boolean randshape; //true for square, false for circle
    ShapeColors randcolor;
    SelectColor selecter;
    ShapeDisplay exampleshapedisplay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartGamebtn = findViewById(R.id.Start);
        Instructions = findViewById(R.id.Instructions);
        exampleshapedisplay = findViewById(R.id.exampleshapedisplay);
        randcolor = selecter.getRandomColor();
        Random randint = new Random();
        randshape = randint.nextInt(2) == 1;


        if (randshape) {
            Instructions.setText("Click on " + randcolor.toString() + " Squares only");
            GradientDrawable exampleshape = new GradientDrawable();
            exampleshape.setShape(GradientDrawable.RECTANGLE);
            exampleshape.setColor(randcolor.color);
            exampleshape.setStroke(2, Color.BLACK);
            exampleshapedisplay.setBackground(exampleshape);
        } else {
            Instructions.setText("Click on " + randcolor.toString() + " circles only");
            GradientDrawable exampleshape = new GradientDrawable();
            exampleshape.setShape(GradientDrawable.OVAL);
            exampleshape.setColor(randcolor.color);
            exampleshape.setStroke(2, Color.BLACK);
            exampleshapedisplay.setBackground(exampleshape);
        }

        StartGamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gamestart= new Intent(getApplicationContext(),GameActivity.class);
                gamestart.putExtra("color",randcolor.color);
                gamestart.putExtra("shape",randshape);
                startActivity(gamestart);
                
            }
        });


    }
}