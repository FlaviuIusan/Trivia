package com.example.fazan;

import android.util.Log;

import java.io.BufferedReader;
import java.util.List;

public class ListenToServer implements Runnable {

    BufferedReader get;
    List<String> listaMesaje;
    public ListenToServer(BufferedReader get, List<String> listaMesaje){
        this.get = get;
        this.listaMesaje = listaMesaje;
    }
    @Override
    public void run() {
        try {
            String line = get.readLine();
            Log.e("mesaj primit", line);
            listaMesaje.add(line);

        } catch (Exception e) {
            Log.e("primit ascultat", "EROARE" + e.toString());
        }

    }
}

