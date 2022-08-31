package com.mad.gamer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.mad.gamer.R;
import com.mad.gamer.adapters.ScoreAdapter;
import com.mad.gamer.model.Model;
import com.mad.gamer.utils.DatabaseHandler;

public class HiScoreScreen extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    RecyclerView recyclerView;
    Button btnPlay;
    ScoreAdapter scoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hi_score_screen);

        databaseHandler = new DatabaseHandler(HiScoreScreen.this);

        recyclerView = findViewById(R.id.recycler_view);
        btnPlay = findViewById(R.id.btn_play);

        scoreAdapter = new ScoreAdapter(HiScoreScreen.this, databaseHandler.readScores());
        recyclerView.setLayoutManager(new LinearLayoutManager(HiScoreScreen.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(scoreAdapter);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HiScoreScreen.this, SequenceScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}