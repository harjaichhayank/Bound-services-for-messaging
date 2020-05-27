package com.example.boundserviceusingmessenger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyService extends Service {

    Messenger messenger = new Messenger(new IncomingHandler());

    static final int JOB_1 = 1;
    static final int JOB_2 = 2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(),"Service is Binding....",Toast.LENGTH_LONG).show();
        return messenger.getBinder();
    }

    class IncomingHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch(msg.what){
                case JOB_1:
                    Toast.makeText(getApplicationContext(),"Hello from JOB 1 it is done",Toast.LENGTH_SHORT).show();
                    break;
                case JOB_2:
                    Toast.makeText(getApplicationContext(),"Hello from JOB 2 it is done",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
