package com.example.fazan;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

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
        listenToServer.interrupt();
    }

    public void send(String message){
        SendToServer sendRun = new SendToServer(send, message);
        Thread sendToServer = new Thread(sendRun);
        sendToServer.start();
    }
}
