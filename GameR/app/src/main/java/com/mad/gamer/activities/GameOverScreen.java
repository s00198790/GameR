package com.mad.gamer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.gamer.R;
import com.mad.gamer.model.Model;
import com.mad.gamer.utils.DatabaseHandler;

import java.util.ArrayList;

public class GameOverScreen extends AppCompatActivity {

    String TAG = "theS";
    int score;
    DatabaseHandler databaseHandler;
    TextView tvScore;
    Button btnPlay, btnHiScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over_screen);

        databaseHandler = new DatabaseHandler(GameOverScreen.this);
        score = getIntent().getIntExtra("score", 4);

        tvScore = findViewById(R.id.tv_score);
        btnPlay = findViewById(R.id.btn_play);
        btnHiScore = findViewById(R.id.btn_hi_score);

        tvScore.setText(getString(R.string.score, String.valueOf(score)));

        if (databaseHandler.canCreateScore(score)){
            showDialog();
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverScreen.this, SequenceScreen.class);
                startActivity(intent);
                finish();
            }
        });

        btnHiScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverScreen.this, HiScoreScreen.class);
                startActivity(intent);
                finish();
            }
        });



    }

    public void showDialog() {
        Dialog dialog = new Dialog(GameOverScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_input);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        EditText etName = dialog.findViewById(R.id.et_name);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String name = etName.getText().toString();
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(GameOverScreen.this, "Please insert name", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, "onClick: "+ databaseHandler.createScore(new Model(name, score)));
                }
            }
        });

        dialog.show();

    }
}