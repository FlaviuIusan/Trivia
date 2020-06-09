package com.example.fazan;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardsActivity extends AppCompatActivity {


    List<String> listaScoruri =  new ArrayList<String>();
    MesajeAdapter topAdapter;

    public class User {
        public String name;
        public int score;

        public User(String name, int score){
            this.name = name;
            this.score = score;
        }

        public User(User user){
            this.name = user.name;
            this.score = user.score;
        }
    }

    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_activity);

        //listaScoruri.add("Alex 2000");

        createMesajeView();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        //databaseReference.child("top10").child("id2").setValue(new User("Andrei", 2000)); //schimba sau, daca nu exista, adauga un copil cu numele id2, copil care are ca valori(copii) string Andrei si int 2000;
        //databaseReference.child("users").child("Flavius890").setValue(new User("Flaviu", 0)); adauga user nou, teoretic trebuie tranzactie pentru cazul in care 2 clienti se inreg cu acelasi username deodata

        //citire baza de date si afisare top 10;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotTop10User : dataSnapshot.child("top10").getChildren()){ //navighez la nodul "top10" si i-au pe rand toate nodurile(userii) acestuia
                    String username =(String) dataSnapshotTop10User.child("username").getValue();
                    Long score = (Long) dataSnapshotTop10User.child("score").getValue();
                    listaScoruri.add(username + " " + score.toString());
                    topAdapter.notifyItemInserted(topAdapter.getItemCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void createMesajeView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        topAdapter = new MesajeAdapter();
        topAdapter.setListaMesaje(listaScoruri);
        recyclerView.setAdapter(topAdapter);
    }
}
