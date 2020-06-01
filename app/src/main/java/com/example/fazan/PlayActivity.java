package com.example.fazan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayActivity extends AppCompatActivity {

    Socket socket;
    private PrintWriter send = null;
    private BufferedReader get = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        connectToServer();
    }

    public void connectToServer(){

        try {
            socket = new Socket("192.168.0.106", 8000);
            send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            get = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }catch (Exception ex){
            Log.e("connection", ex.toString());
        }

    }
}
