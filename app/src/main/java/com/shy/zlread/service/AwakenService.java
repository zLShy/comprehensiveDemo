package com.shy.zlread.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.shy.zlread.ProcessConnection;
import com.shy.zlread.R;

/**
 * Created by zhangli on 2019/7/10.
 */

public class AwakenService extends Service {

    private final int MessageId = 0;
    private static final String TAG = AwakenService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e(TAG, "runing.....");
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {
        };
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.s_0_1);
        mBuilder.setContentTitle("zllll");
        Notification notification = mBuilder.build();
        startForeground(MessageId, new Notification());
        bindService(new Intent(this, GuardService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(AwakenService.this, "建立链接...", Toast.LENGTH_SHORT).show();
            Log.e(TAG,TAG+"建立链接...");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(AwakenService.this, GuardService.class));
            bindService(new Intent(AwakenService.this, GuardService.class), mServiceConnection, Context.BIND_IMPORTANT);

        }
    };
}
