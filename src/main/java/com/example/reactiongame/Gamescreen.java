package com.example.reactiongame;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/*
this class handles all the logic for the game and the actual drawing on the gamescreen
 */

public class Gamescreen extends View {
    private static final long GAME_START_TIME = 999999999;
    Handler h;
    int frameRate;
    int state = 0;
    int score, poppedShapeCount, missedShapeCount;
    CountDownTimer timer;
    long currentTime;
    List<Shape> Shapelist; //list of active shapes

    boolean currentShape; //true = SQUARE , false = CIRCLE
    int currentColor;
    int scoretime=0;

    int canvasHeight, canvasWidth;

    int numbshapes; //number of shapes

    TextView scoreText, timerText;

    LinearLayout resultView;
    Button viewScoresButton;
    TextView result;


    public Gamescreen(Context context) {
        super(context);
        init(context);
    }


    /*
     * Initialize the handler and shape list
     *
     * */
    public void init(Context context){
        h = new Handler();
        frameRate = 1;
        Shapelist = new LinkedList<Shape>();
    }

    public Gamescreen(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    /*
     * Set current games shape
     * */
    public void setCurrentGame(boolean shape, int color){
        this.currentShape = shape;
        this.currentColor = color;
    }

    public void setTextViews(TextView score, TextView timer){
        scoreText = score;
        timerText = timer;
    }

    public void setResultView(LinearLayout resultView, TextView result, Button viewScores){
        this.resultView = resultView;
        this.result = result;
        this.viewScoresButton = viewScores;
    }


    /*
     * This handles the slecting of shapes
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(state == 1) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Shape touchedShape= null;

                for (Shape currshape : Shapelist) {
                    if (currshape.xpos < event.getX() && event.getX() < currshape.xpos + currshape.Shapesize && currshape.ypos > event.getY() && event.getY() > currshape.ypos - currshape.Shapesize) {
                        touchedShape = currshape;

                    }
                }


                if (touchedShape != null) {
                    if (touchedShape.ShapeColor == currentColor && touchedShape.getShape() == currentShape) {
                        score++; //increment score if correct balloon
                        poppedShapeCount++;
                    } else {

                    }



                    scoreText.setText(String.valueOf(score));



                    Shapelist.remove(touchedShape);
                    if (timer != null && poppedShapeCount % 10 == 0 && poppedShapeCount != 0) {
                        timer.cancel();
                        startTimer(currentTime + 10000);
                        timerText.setText("+10s");
                    }

                }


            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        //if it is the start if game then make inital set
        if(state == 0){

            Random rand = new Random();
            numbshapes = rand.nextInt((12 - 6) + 1) + 6;
            //generate random number of shapes
            for(int i=0 ; i< numbshapes ; i++){
                if(rand.nextInt(2) == 1){
                    Shapelist.add(new Square(getContext(),canvasHeight,canvasWidth));
                }else{
                    Shapelist.add(new Circle(getContext(),canvasHeight, canvasWidth));
                }

            }
            //start the timer and change state
            startTimer(GAME_START_TIME);
            state++;

        }

        //Check if a shape reaches any boundry of the screen, set it to be disappeared

        List<Shape> disappeardBalloons = new LinkedList<>();

        for(Shape shape : Shapelist){
            if(shape.ypos <= 0 || shape.ypos > canvasHeight + shape.Shapesize|| shape.xpos <= 0 || shape.ypos > canvasWidth + shape.Shapesize ){
                if(shape.ShapeColor == currentColor && shape.getShape() == currentShape){
                    missedShapeCount++;
                }
                disappeardBalloons.add(shape);
            }
            shape.lifetime--;
            if(shape.lifetime<=0){
                disappeardBalloons.add(shape);
            }
        }

        //remove disappeared shapes from list
        for(Shape balloon : disappeardBalloons){
            Shapelist.remove(balloon);
        }

        // check the number of shapes disappeared or popped
        int diff = numbshapes - Shapelist.size();

        //create new random shapes when they are popped or disappear
        for(int i=0; i < diff ; i++){
            Random rand = new Random();
            if(rand.nextInt(2) == 1){
                Shapelist.add(new Square(getContext(),canvasHeight,canvasWidth));
            }else{
                Shapelist.add(new Circle(getContext(),canvasHeight, canvasWidth));
            }
        }

        //Check if shapes overlap
        for(Shape shape : Shapelist){
            if( state != 0) {
                for (Shape other : Shapelist) {
                    //check if shapes intersect
                    if (shape.shapeobject.intersect(other.shapeobject)) {

                        if (shape.ypos < other.ypos) {


                            shape.xpos= shape.xpos +1;
                            shape.incrementspeed(2);
                            other.decrementspeed(2);
                        }else{
                            other.xpos= other.xpos +1;
                            shape.incrementspeed(2);
                            other.decrementspeed(2);
                        }
                    }
                }

            }

            shape.DrawShape(canvas);

        }


        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        },10);

    }


    /*
     * Start the timer and set ontick and onfinish
     * */
    private void startTimer(long time){
        timer = new CountDownTimer(time, 1000){
            public void onTick(long millisUntilDone){
                scoretime++;
                timerText.setText( String.valueOf(scoretime) + "s");
                if( score == 1){
                    timerText.setText("DONE!");
                    onFinish();
                    cancel();
                }
            }

            public void onFinish() {
                state = 2;
                resultView.setVisibility(VISIBLE);
                result.setVisibility(VISIBLE);
                viewScoresButton.setVisibility(VISIBLE);

                final int finalscore=scoretime;
                result.setText("You scored " + finalscore + " You missed: "+missedShapeCount+ " Shapes");
                viewScoresButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), NewScore.class);
                        i.putExtra("score", finalscore);
                        getContext().startActivity(i);
                    }
                });
                //timerText.setText("DONE!");
            }
        };
        timer.start();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


}
