package com.example.fazan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PlayActivityBReceiver extends BroadcastReceiver {


    MesajeAdapter mesajeAdapter;

    public PlayActivityBReceiver(MesajeAdapter mesajeAdapter){
        this.mesajeAdapter = mesajeAdapter;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        String actiunea = intent.getAction();
        if(actiunea.compareTo("RefreshMesajeView")==0){
            mesajeAdapter.notifyItemInserted(mesajeAdapter.getItemCount());
        }
    }
}
