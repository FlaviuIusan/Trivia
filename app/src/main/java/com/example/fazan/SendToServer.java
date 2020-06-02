package com.example.fazan;

import android.util.Log;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class SendToServer implements Runnable {

    PrintWriter send;
    String message;
    public SendToServer(PrintWriter send, String message){
        this.send = send;
        this.message=message;
    }

    @Override
    public void run() {
        try {
            send.println(message + "\r\n");

        } catch (Exception e) {
            Log.e("erroare trimis", "Nu s-a trimis");
        }
    }
}
