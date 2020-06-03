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
            String line;
            while(!get.ready()){

            }
            line = get.readLine();
            Log.e("primit mesaj", "Linie " + line);
            listaMesaje.add(line);
            for(int i = 0; i<listaMesaje.size(); ++i) {
                Log.e("elemente lista", listaMesaje.get(i));
            }

        } catch (Exception e) {
            Log.e("primit ascultat", "EROARE" + e.toString());
        }

    }
}

