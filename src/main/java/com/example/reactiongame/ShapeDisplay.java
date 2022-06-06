package com.example.reactiongame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;
/*
    this class is used for displayinga shape for the user on the manin menu
 */

public class ShapeDisplay extends View {
    Shape displayshape;

    public ShapeDisplay(Context context){
        super(context);
        init(context);
    }

    public void init(Context context){

    }

    public ShapeDisplay(Context context, AttributeSet attributes){
        super(context,attributes);
        init(context);
    }
    public void SetShape(Shape inshape){
        this.displayshape=inshape;
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }


}
