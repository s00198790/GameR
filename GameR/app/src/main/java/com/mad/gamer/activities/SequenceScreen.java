package com.mad.gamer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mad.gamer.utils.CustomCircle;
import com.mad.gamer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SequenceScreen extends AppCompatActivity {

    FrameLayout frameLayout;
    Button btnPlay;
    String TAG = "theS";
    int score = 0;
    int totalCircle = 4;
    float radius = 50, cx = 0, cy = 120;
    List<Integer> colorList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sequence_screen);

        frameLayout = findViewById(R.id.frame_layout);
        btnPlay = findViewById(R.id.btn_play);

        totalCircle = getIntent().getIntExtra("totalCircle", totalCircle);
        score = getIntent().getIntExtra("score", score);

        creteColorList();
        createCircle();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SequenceScreen.this, PlayScreen.class);
                intent.putExtra("totalCircle", totalCircle);
                intent.putExtra("score", score);
                intent.putIntegerArrayListExtra("colorList", (ArrayList<Integer>) colorList);
                startActivity(intent);
                finish();
            }
        });

    }

    public void creteColorList() {
        colorList.add(Color.BLACK);
        colorList.add(Color.BLUE);
        colorList.add(Color.CYAN);
        colorList.add(Color.GRAY);
        colorList.add(Color.GREEN);
        colorList.add(Color.LTGRAY);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.RED);
        colorList.add(Color.YELLOW);

        Collections.shuffle(colorList);

        if (totalCircle > colorList.size()) {
            int diff = totalCircle - colorList.size();
            for (int i = 0; i <= diff; i++) {
                colorList.add(colorList.get(i));
            }
        }

        Collections.shuffle(colorList);
    }

    public void createCircle() {
        for (int i = 0; i < totalCircle; i++) {
            cx = cx + cy;
            CustomCircle circle = new CustomCircle(SequenceScreen.this);
            circle.init(i,colorList.get(i), radius);
            circle.move(cx, cy);
            circle.invalidate();
            Log.d(TAG, "SequenceScreen: createCircle: " + circle);
            frameLayout.addView(circle);
        }
    }

}