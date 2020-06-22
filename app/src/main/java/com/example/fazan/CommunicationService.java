package com.example.fazan;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

public class CommunicationService extends Service {


    PrintWriter send;
    BufferedReader get;

    List<String> listaMesaje;

    User user;

    public void setUser(User user){
        this.user = user;
    }

    public void setListaMesaje(List<String> listaMesaje){
        this.listaMesaje = listaMesaje;
    }

    public void setSentObj(PrintWriter send){
        this.send = send;
    }

    public void setGetObj(BufferedReader get){
        this.get = get;
    }

    private final IBinder iBinder= new LocalBinder();

    public class LocalBinder extends Binder {
        CommunicationService getService() {

            return CommunicationService.this;
        }
    }


    private final class listenRun implements Runnable{

        @Override
        public void run() {
            while(true) {
                try {
                    String line;
                    if(get != null) {
                        if ((line = get.readLine()) != null) {
                            if(line.toString().compareTo("ServerX: " + user.userId + " a raspuns corect !!!")==0){
                                Intent intent = new Intent();
                                intent.setAction("UpdateUserScore");
                                sendBroadcast(intent);
                            }
                            else if(line.toString().contains("ServerX")){

                            }
                            else {

                                Log.e("listenRunFunc", "Mesajul primit este " + line);
                                listaMesaje.add(line);

                                Intent intent = new Intent();
                                intent.setAction("RefreshMesajeView");
                                sendBroadcast(intent);
                            }
                        }
                            Log.e("listenRunFunc", "Mesajul a fos null");
                    }


                } catch (Exception e) {
                    Log.e("Exceptie primit", e.toString());
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Boolean firstMessage = true;

    private final class sendRun implements Runnable{

        @Override
        public void run() {
            try {
                if(firstMessage){
                    send.println(user.userId + "- " + user.username + ": " + mesaj);
                    Log.e("sendRunFunc", "S-a trimis" + user.username);
                    firstMessage = false;
                }
                else {
                    send.println(user.username + ": " + mesaj);
                    Log.e("sendRunFunc", "S-a trimis" + user.username);
                }

            } catch (Exception e) {
                Log.e("erroare trimis", "Nu s-a trimis");
            }
        }
    }

    Thread listenToServerT;
    Thread sendToServerT;
    String mesaj;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        listen();
        return iBinder;
    }

    public void listen(){
        listenToServerT = new Thread(new listenRun());
        Log.e("listenFunc", "incep asculttttt");
        listenToServerT.start();
    }

    public void send(String mesaj){

        this.mesaj = mesaj;
        sendToServerT = new Thread(new sendRun());
        sendToServerT.start();
        Log.e("sendFunction", "S-a trimis");
    }
}