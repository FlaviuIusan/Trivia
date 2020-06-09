package com.example.fazan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

public class LoginSignupActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsignup_activity);

        final AppCompatEditText EditTextUsername = findViewById(R.id.EditTextUsername);

        //trebuie verificat daca exista username-ul respectiv / sau il creez si atentionez userul ca s-a creat cont nou si dupa accea trimit la mainActivity.
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditTextUsername.getText()==null){
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
                else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("Username Result", EditTextUsername.getText().toString()); //grija .toString() ca altfel nu e string.
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

    }
}
