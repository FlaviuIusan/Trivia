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



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }




    public void listen(){
        ListenToServer listenRun = new ListenToServer(get, listaMesaje);
        Thread listenToServer = new Thread(listenRun);
        listenToServer.start();

    }

    public void send(String message){


        SendToServer sendRun = new SendToServer(send, message);
        Thread sendToServer = new Thread(sendRun);
        sendToServer.start();
    }
}
