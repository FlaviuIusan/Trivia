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

    Looper serviceLooper;
    ServiceHandler serviceHandler;
    Thread listenToServer;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
                if(msg.arg1 == 1) {
                    ListenToServer listenRun = new ListenToServer(get, listaMesaje);
                    listenToServer = new Thread(listenRun);
                    listenToServer.start();
                    Log.e("message 1", "asculttttt");
                }
                if(msg.arg1 == 2){


                }
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }

    @Override
    public void onCreate() {

        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                HandlerThread.MIN_PRIORITY    );
        thread.start();

        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = 1;
        serviceHandler.sendMessage(msg);
        Log.e("MyService", "Munca a inceput pentru id:" + startId);

        return START_STICKY;
    }

    public void listen(){
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = 1;
        serviceHandler.sendMessage(msg);
    }

    public void send(String message){

        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = 2;
        msg.obj = message;
        serviceHandler.sendMessage(msg);
    }
}
