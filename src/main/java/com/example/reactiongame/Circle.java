package com.example.reactiongame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/*
this class will hold attributes specific to a square
 */

public class Circle extends Shape {
    public Circle(Context context, int scrheight, int scrwidth){
        super(context, scrheight, scrwidth);
    }
    @Override
    void DrawShape(Canvas canvas){
        shapeobject = new RectF(xpos,ypos-Shapesize ,xpos+Shapesize,ypos);
        Paint paint= new Paint();
        paint.setColor(ShapeColor);
        canvas.drawCircle(xpos+Shapesize/2,ypos-Shapesize/2,Shapesize/2,paint);
        ypos-=speed;
    }
    public float getTop() {
        return ypos-Shapesize/2;
    }
    boolean getShape(){
        return false;
    }

}
