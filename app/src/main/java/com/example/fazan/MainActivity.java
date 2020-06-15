package com.example.fazan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    User user;
    Credentials userCredentials;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        user = new User();

        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user.userId.compareTo("")!=0) {
                    Log.e("Data user", user.username + " " + user.userId);

                    Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                    intent.putExtra("User Data", user);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Trebuie sa te conectezi mai intai !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.buttonLeaderboards).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderboardsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonLoginRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Auth0 auth0 = new Auth0(MainActivity.this);
                final AuthenticationAPIClient authentication = new AuthenticationAPIClient(auth0);
                auth0.setOIDCConformant(true);
                auth0.setLoggingEnabled(true);

                WebAuthProvider.login(auth0) //open login page
                        .withScheme("demo")
                        .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                        .start(MainActivity.this, new AuthCallback() {
                            @Override
                            public void onFailure(@NonNull Dialog dialog) {
                                // Show error Dialog to user
                            }

                            @Override
                            public void onFailure(AuthenticationException exception) {
                                // Show error to user
                            }

                            @Override
                            public void onSuccess(@NonNull final Credentials credentials) {

                                userCredentials = credentials;
                                Log.e("Login", userCredentials.getAccessToken());

                                authentication
                                        .userInfo(userCredentials.getAccessToken())
                                        .start(new BaseCallback<UserProfile, AuthenticationException>() {
                                            @Override
                                            public void onSuccess(UserProfile information) {
                                                user.userId = information.getId();
                                                Log.e("User Id", user.userId);

                                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.child("users").hasChild(user.userId)) {
                                                            user.score = (long) dataSnapshot.child("users").child(user.userId).child("score").getValue();
                                                            user.username = (String) dataSnapshot.child("users").child(user.userId).child("username").getValue();

                                                        } else {
                                                            Log.e("else", "else else");
                                                            databaseReference.child("users").child(user.userId).setValue(new User(user.userId, 0, null));
                                                            user.username = user.userId;
                                                            user.score = 0;
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(AuthenticationException error) {
                                                //user information request failed
                                            }
                                        });
                            }
                        });


            }
        });

        findViewById(R.id.buttonAccountSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.userId.compareTo("")!=0) {
                    Intent intent = new Intent(MainActivity.this, AccountSettingsActivity.class);
                    intent.putExtra("Username", user.username);
                    startActivityForResult(intent, 1);
                }else{
                    Toast.makeText(MainActivity.this, "Trebuie sa te conectezi mai intai !!!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            Bundle results = data.getExtras();
            user.username = results.getString("Username");
            Log.e("Result", user.username);
            databaseReference.child("users").child(user.userId).child("username").setValue(user.username);
        }

    }
}
