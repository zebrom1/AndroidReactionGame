package com.example.reactiongame;

/*
this class is to store the game colors and return a random color for the game
 */
import android.graphics.Color;
import java.util.Random;


public class SelectColor {
    public static final ShapeColors[] shapeColors = new ShapeColors[]{new ShapeColors(Color.RED, "Red"),new ShapeColors(Color.rgb(255, 165, 0), "Orange"),
            new ShapeColors(Color.YELLOW, "Yellow") , new ShapeColors(Color.WHITE, "White"), new ShapeColors(Color.GREEN, "Green"),
            new ShapeColors(Color.BLUE, "Blue"), new ShapeColors(Color.rgb(128,0,128), "Purple") };


    public static ShapeColors getRandomColor(){
        Random randcolor= new Random();
        int randomColorIndex= randcolor.nextInt(shapeColors.length);
        return shapeColors[randomColorIndex];

    }

}
