package com.example.reactiongame;

/*
    this class for storing a color and it's name.
 */

public class ShapeColors {
    public int color;
    public String name;
    public ShapeColors(int incolor,String inname){
        this.color=incolor;
        this.name=inname;

    }
    @Override
    public String toString(){
        return name;
    }
}
