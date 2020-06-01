package com.example.fazan;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

import java.security.Provider;
import java.util.List;
import java.util.Map;

public class CommunicationService extends Service {


    private IBinder iBinder= new LocalBinder();

    public class LocalBinder extends Binder {
        CommunicationService getService() {

            return CommunicationService.this;
        }
    }

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    private final class ServiceHandler extends Handler{

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
}
