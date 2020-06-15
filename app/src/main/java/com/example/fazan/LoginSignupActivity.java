package com.example.fazan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.auth0.android.Auth0;
import com.auth0.android.Auth0Exception;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.request.ProfileRequest;
import com.auth0.android.authentication.storage.CredentialsManagerException;
import com.auth0.android.authentication.storage.SecureCredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.management.ManagementException;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.VoidCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginSignupActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsignup_activity);



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference();

        final AppCompatEditText EditTextUsername = findViewById(R.id.EditTextUsername);

        //trebuie verificat daca exista username-ul respectiv / sau il creez si atentionez userul ca s-a creat cont nou si dupa accea trimit la mainActivity.
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditTextUsername.getText()==null){
                    Toast.makeText(LoginSignupActivity.this, "Campul Username nu poate fi gol !!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String username = EditTextUsername.getText().toString(); //grija .toString() ca altfel nu e string.
                            User userData = new User();
                            if(dataSnapshot.child("users").hasChild(username)){
                                userData.score = (long) dataSnapshot.child("users").child(username).child("score").getValue();
                                userData.username = username;
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("User Data", userData);
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();

                            }
                            else{
                                Toast.makeText(LoginSignupActivity.this, "Username-ul nu exista", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditTextUsername.getText()==null){
                    Toast.makeText(LoginSignupActivity.this, "Campul Username nu poate fi gol !!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() { //verific sa nu existe deja un user cu username-ul introdus
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("users").hasChild(EditTextUsername.getText().toString())){
                                Toast.makeText(LoginSignupActivity.this, "Username-ul exista deja !!!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("users").child(EditTextUsername.getText().toString()).setValue(new User(null, 0, null));
                                Toast.makeText(LoginSignupActivity.this, "Username-ul a fost inregistrat !!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


    }

}


