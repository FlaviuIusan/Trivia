package com.example.fazan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

        findViewById(R.id.buttonLoginRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginSignupActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        //trebuie fereastra login (activity for result) si sign up
        //cand intru in activity de chat trebuie sa trimit username si punctaj si updatez punctaj daca dau raspuns corect.
        //cand ies din chat trebuie updatada baza de date cu noul punctaj.
        //functie de adaugare intrebare noua(asta trebuie in server si conectat serverul la firebase);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            String username = extras.getString("Username Result");
            Toast toast = Toast.makeText(MainActivity.this, username, Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(MainActivity.this, "eroare", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
