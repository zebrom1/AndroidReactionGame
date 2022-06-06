package com.example.reactiongame;

/*
    this class is to represent a shape in an abstract class
 */


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;


import java.util.Random;


public abstract class Shape { //this will generate a shape it's attributes
    int Shapesize;
    public int ShapeColor;
    float xpos;
    float ypos;
    int speed;
    int lifetime;
    SelectColor coloragent;//mearly used for getting methods
    PixelConvert sizeagent;
    RectF shapeobject;
    public static final int Max_speed=20;
    public static final int Min_speed=2;
    public Shape(Context context, int scrheight, int scrwidth){
        Random randspeed= new Random();
        int randSize= randspeed.nextInt((64-32)+1)+32;
        this.ShapeColor=coloragent.getRandomColor().color;
        this.Shapesize=sizeagent.dip2px(context,randSize);
        this.xpos = randspeed.nextInt(scrheight- this.Shapesize);
        this.ypos = randspeed.nextInt(scrwidth- this.Shapesize);
        Random randomlife= new Random();
        lifetime= randomlife.nextInt(700-300)+300;
        this.speed= randspeed.nextInt(10 + 10)-10;
        shapeobject=new RectF(xpos,ypos-Shapesize, xpos+Shapesize,ypos);
    }

    abstract void DrawShape(Canvas canvas);
    abstract float getTop();
    abstract boolean getShape();

    public void incrementspeed(int addspeed){
        if(speed + addspeed<= Max_speed){
            speed+=addspeed;
        }
    }
    public void decrementspeed(int subspeed){
        if(speed +subspeed>= Min_speed){
            speed-=subspeed;
        }
    }


}
