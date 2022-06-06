package com.example.reactiongame;


/*
this class is used to convert from DP to Pixels
 */
import android.content.Context;
import android.util.TypedValue;

public class PixelConvert {

    public static int pxtodp(int inpx, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,inpx, context.getResources().getDisplayMetrics());
    }

    public static int dip2px(Context context, float indp){
        final float scale= context.getResources().getDisplayMetrics().density;
        return (int) (indp* scale + .5f);
    }
}
