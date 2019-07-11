package com.shy.zlread.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.shy.zlread.UserAidl;

/**
 * Created by zhangli on 2019/7/5.
 */

public class MessageService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinber;
    }

    private UserAidl.Stub mBinber = new UserAidl.Stub(){

        @Override
        public String getuserName() throws RemoteException {
            return "zl";
        }

        @Override
        public String getpassword() throws RemoteException {
            return "zl123";
        }
    };

    public class TestBinder extends Binder {
        public MessageService getService() {
            return MessageService.this;
        }
    }
}
