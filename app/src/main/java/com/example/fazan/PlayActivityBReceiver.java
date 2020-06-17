package com.example.fazan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

public class PlayActivityBReceiver extends BroadcastReceiver {


    MesajeAdapter mesajeAdapter;
    User user;
    DisplayUserDataFragment fragment;
    RecyclerView recyclerView;

    public PlayActivityBReceiver(MesajeAdapter mesajeAdapter, User user, DisplayUserDataFragment fragment, RecyclerView recyclerView){
        this.mesajeAdapter = mesajeAdapter;
        this.user = user;
        this.fragment = fragment;
        this.recyclerView = recyclerView;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        String actiunea = intent.getAction();
        if(actiunea.compareTo("RefreshMesajeView")==0){
            mesajeAdapter.notifyItemInserted(mesajeAdapter.getItemCount());
            recyclerView.smoothScrollToPosition(mesajeAdapter.getItemCount());
        }
        if(actiunea.compareTo("UpdateUserScore")==0){
            user.score = user.score + 1;
            fragment.changeTextInTextViewScore(Integer.valueOf((int)user.score).toString());
        }
    }
}
