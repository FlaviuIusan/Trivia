package com.example.fazan;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    Socket socket;
    PrintWriter send;
    BufferedReader get;
    List<String> listaMesaje =  new ArrayList<String>();
    MesajeAdapter mesajeAdapter;
    CommunicationService communicationService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        listaMesaje.add("Jocul a inceput !!!");

        connectToServer();

        createMesajeView();

        listenToBroadcasts();

        startCommunication();

        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextChat = findViewById(R.id.editTextChat);
                String mesaj = editTextChat.getText().toString();
                try{
                    communicationService.send(mesaj);
                }
                catch (Exception ex){
                    Log.e("send", ex.toString());
                }
            }
        });

    }



    @SuppressLint("StaticFieldLeak")
    public void connectToServer(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    socket = new Socket("192.168.0.106", 8000);
                    send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    get = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                }catch (Exception ex){
                    Log.e("connection", ex.toString());
                }
                return null;
            }
        }.execute();
    }


    public void createMesajeView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mesajeAdapter = new MesajeAdapter();
        mesajeAdapter.setListaMesaje(listaMesaje);
        recyclerView.setAdapter(mesajeAdapter);
    }


    public void listenToBroadcasts(){
        BroadcastReceiver BroadcastReceiver = new PlayActivityBReceiver(mesajeAdapter);
        IntentFilter filter = new IntentFilter();
        filter.addAction("RefreshMesajeView");
        PlayActivity.this.registerReceiver(BroadcastReceiver, filter);
    }


    public void startCommunication(){

        ServiceConnection connection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className,
                                           IBinder service) {
                CommunicationService.LocalBinder binder = (CommunicationService.LocalBinder) service;
                binder.getService().setGetObj(get);
                binder.getService().setSentObj(send);
                binder.getService().setListaMesaje(listaMesaje);
                communicationService = binder.getService();
                Log.e("creeare", "AICICICICICICICICICI");

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        Intent intent = new Intent(getApplicationContext(), CommunicationService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE); // unbindService(connection);
    }

}
