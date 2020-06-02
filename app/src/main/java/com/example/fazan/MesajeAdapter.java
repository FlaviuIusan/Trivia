package com.example.fazan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MesajeAdapter extends RecyclerView.Adapter<MesajeAdapter.ViewHolder> {

    List<String> listaMesaje;

    public void setListaMesaje(List<String> listaMesaje){
        this.listaMesaje = listaMesaje;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesaj_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String mesaj = listaMesaje.get(position);
        holder.textViewMesaj.setText(mesaj);
    }

    @Override
    public int getItemCount() {
        return listaMesaje.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMesaj = itemView.findViewById(R.id.textViewMesaj);
        }
        TextView textViewMesaj;
    }
}
