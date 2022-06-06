package com.example.reactiongame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
this will hold the basic setting of elements within the game activity
 */

public class GameActivity extends AppCompatActivity{
    private ViewGroup mContentView;
    Gamescreen gamescreen;
    int mScreenWidth, mScreenHeight;
    TextView scoreText, timerText, timerTop, scoreTop;

    int currentColor;
    boolean currentShape;

    private SensorManager mSensorMgr;
    private Sensor accelerometerSensor;
    static final float ALPHA = 0.25f; // if ALPHA = 1 OR 0, no filter applies.
    float previousX;

    LinearLayout resultView;
    Button viewScoresButton;
    TextView result;

    private long lastUpdateTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mContentView = findViewById(R.id.activity_game);

        Intent intent = getIntent();
        currentShape = intent.getBooleanExtra("shape", true);
        currentColor = intent.getIntExtra("color", 0);

        gamescreen = findViewById(R.id.gamescreen);
        scoreText = findViewById(R.id.score);
        timerText = findViewById(R.id.timer);
        timerTop = findViewById(R.id.timerTop);
        scoreTop = findViewById(R.id.scoreTop);
        resultView = findViewById(R.id.resultView);
        result = findViewById(R.id.result);
        viewScoresButton = findViewById(R.id.viewScores);

        gamescreen.setCurrentGame(currentShape, currentColor);

        gamescreen.setTextViews(scoreText, timerText);
        gamescreen.setResultView(resultView, result, viewScoresButton);

        result = findViewById(R.id.result);
        viewScoresButton = findViewById(R.id.viewScores);


        //Setting border
        gamescreen.setBackground(getResources().getDrawable(R.drawable.border));


        //Initializing the sensor manager
        mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mScreenHeight = mContentView.getHeight();
                    mScreenWidth = mContentView.getWidth();
                }
            });
        }
    }
}
