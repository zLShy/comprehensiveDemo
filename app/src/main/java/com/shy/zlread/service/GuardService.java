package com.shy.zlread.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.shy.zlread.ProcessConnection;

/**
 * Created by zhangli on 2019/7/11.
 */

public class GuardService extends Service {

    private int GuardId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(GuardId, new Notification());
        bindService(new Intent(this, AwakenService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {
        };
    }

    private String TAG = GuardService.class.getSimpleName();
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Toast.makeText(GuardService.this,"建立链接...",Toast.LENGTH_SHORT).show();
            Log.e(TAG,TAG+"建立链接...");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            startService(new Intent(GuardService.this, AwakenService.class));
            bindService(new Intent(GuardService.this, AwakenService.class), mServiceConnection, Context.BIND_IMPORTANT);

        }
    };
}
