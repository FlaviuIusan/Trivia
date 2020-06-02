package com.example.fazan;

import android.util.Log;

import java.io.BufferedReader;

public class ListenToServer implements Runnable {

    BufferedReader get;
    public ListenToServer(BufferedReader get){
        this.get = get;
    }
    @Override
    public void run() {
        try {
            String line = null;

            while(get.readLine() != null)
            {
                line = get.readLine();
                Log.e("Primit Ascultat", line);

            }
        } catch (Exception e) {
            // ...
        }

    }
}

