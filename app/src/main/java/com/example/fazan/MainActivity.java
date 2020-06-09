package com.example.fazan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonLeaderboards).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderboardsActivity.class);
                startActivity(intent);
            }
        });

        //trebuie fereastra login (activity for result) si sign up
        //cand intru in activity de chat trebuie sa trimit username si punctaj si updatez punctaj daca dau raspuns corect.
        //cand ies din chat trebuie updatada baza de date cu noul punctaj.
        //functie de adaugare intrebare noua(asta trebuie in server si conectat serverul la firebase);
    }
}
