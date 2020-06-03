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

        String line;

        while(true) {
            try {
                line = get.readLine();
                if(line!=null) {

                    Log.e("primit mesaj", "Linie " + line);
                    listaMesaje.add(line);
                }
            } catch (Exception e) {
                Log.e("primit ascultat", "EROARE" + e.toString());
            }
           try {
               Thread.sleep(1000);
           }catch (Exception ex){
               Log.e("thread listen", "failed to sleep");
           }
        }
    }
}

