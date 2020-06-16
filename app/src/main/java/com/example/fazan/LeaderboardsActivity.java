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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardsActivity extends AppCompatActivity {


    List<String> listaScoruri =  new ArrayList<String>();
    MesajeAdapter topAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_activity);

        createMesajeView();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        Query myTopUsersQuery = databaseReference.child("users").orderByChild("score").limitToLast(10);
        myTopUsersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotTop10User : dataSnapshot.getChildren()){
                    String username = (String) dataSnapshotTop10User.child("username").getValue();
                    Long score = (Long) dataSnapshotTop10User.child("score").getValue();
                    listaScoruri.add(0, username + " " + score.toString());
                    topAdapter.notifyItemInserted(0);
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
