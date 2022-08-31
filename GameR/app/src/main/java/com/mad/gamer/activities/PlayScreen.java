package com.mad.gamer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mad.gamer.model.Circle;
import com.mad.gamer.utils.CustomCircle;
import com.mad.gamer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayScreen extends AppCompatActivity implements SensorEventListener {

    String TAG = "theS";
    TextView tvText;
    StringBuilder builder = new StringBuilder();
    FrameLayout frameLayout;
    SensorManager sensorManager;
    Sensor sensor;
    int totalCircle = 4, currentCircle = 0;
    List<Integer> colorList;
    List<CustomCircle> circleList = new ArrayList<>();
    float radius = 50, cx = 0, cy = 120;

    int score = 0;
    int width = 0, height = 0, newWidth = 0, newHeight = 0, cross = 0;
    boolean checkResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_screen);

        tvText = findViewById(R.id.tv_text);
        frameLayout = findViewById(R.id.frame_layout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        newWidth = width / 10;
        newHeight = height / 10;
        cross = (height - (newHeight * 2));
        Log.d(TAG, "onCreate: width " + width + " height " + height + " cross: " + cross);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(PlayScreen.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        score = getIntent().getIntExtra("score", score);
        totalCircle = getIntent().getIntExtra("totalCircle", totalCircle);
        colorList = getIntent().getIntegerArrayListExtra("colorList");

        createCircle();

    }

    float sides = 0, upDown = 0;
    float x = 0, y = 0;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        builder = new StringBuilder();

        // Sides = Tilting phone left(10) and right(-10)
        sides = sensorEvent.values[0];
        // Up/Down = Tilting phone up(10), flat (0), upside-down(-10)
        upDown = sensorEvent.values[1];

        if (sides > 0) {
            x = sides * newHeight;
        }

        if (upDown > 0) {
            y = upDown * newWidth + radius;
        }

        if (!checkResult) {
            circleList.get(currentCircle).move(y, x);
            circleList.get(currentCircle).invalidate();
        }

        if (x >= cross) {
            Log.d(TAG, "onSensorChanged: currentCircle: " + currentCircle);
            if (currentCircle <= (totalCircle - 1)) {
                currentCircle++;
                if (sensorManager != null) {
                    sensorManager.unregisterListener(PlayScreen.this);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sensorManager.registerListener(PlayScreen.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                        }
                    }, 500);
                }

                if (currentCircle == totalCircle) {
                    checkResult = true;
                    if (sensorManager != null)
                        sensorManager.unregisterListener(PlayScreen.this);
                    calculateResult();
                }
            }
        }
//
//        builder.append("sides ").append(sides).append("\n");
//        builder.append("upDown ").append(upDown).append("\n");
//        builder.append("x ").append(y).append("\n");
        builder.append("Value should be greater the or equal to ").append(cross).append(", Value = ").append(x).append("\n");
        tvText.setText(builder);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null)
            sensorManager.unregisterListener(PlayScreen.this);
    }

    public void createCircle() {

        for (int i = 0; i < totalCircle; i++) {
            CustomCircle circle = new CustomCircle(PlayScreen.this);
            circle.init(i, colorList.get(i), radius);
            circleList.add(circle);
        }

        Collections.shuffle(circleList);

        for (CustomCircle circle : circleList) {
            cx = cx + cy;
            circle.move(cx, cy);
            circle.invalidate();
            Log.d(TAG, "PlayScreen: createCircle: " + circle);
            frameLayout.addView(circle);
        }
    }


    public void calculateResult() {
        ProgressDialog progressDialog = ProgressDialog.show(PlayScreen.this, "", "Please Wait", true);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Collections.sort(circleList, new Comparator<CustomCircle>() {
                    @Override
                    public int compare(CustomCircle o1, CustomCircle o2) {
                        return o1.id - o2.id;
                    }
                });

                float temp = 0;
                boolean isMatched = false;
                for (CustomCircle circle : circleList) {
                    Log.d(TAG, "onSensorChanged: " + circle);
                    if (circle.cx >= temp) {
                        temp = circle.cx;
                        isMatched = true;
                    } else {
                        isMatched = false;
                        break;
                    }

                }


                Intent intent;
                if (isMatched) {
                    score = score + 4;
                    intent = new Intent(PlayScreen.this, SequenceScreen.class);
                    intent.putExtra("totalCircle", (totalCircle + 2));
                } else {
                    intent = new Intent(PlayScreen.this, GameOverScreen.class);
                }

                Log.d(TAG, "calculateResult: " + isMatched + " score: " + score);
                intent.putExtra("score", score);
                progressDialog.dismiss();
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}