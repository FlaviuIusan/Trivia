package com.example.fazan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

public class AccountSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsettings_activity);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final AppCompatEditText EditTextUsername = findViewById(R.id.EditTextUsername);
        Log.e("Username", extras.getString("Username") + "jkl");
        EditTextUsername.setText(extras.getString("Username"));
        findViewById(R.id.buttonSaveSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditTextUsername.getText().toString().compareTo("")!=0) {
                    Intent intent = new Intent();
                    intent.putExtra("Username", EditTextUsername.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast.makeText(AccountSettingsActivity.this, "Introduceti un username !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
