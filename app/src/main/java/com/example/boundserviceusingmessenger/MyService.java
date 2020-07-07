package com.example.boundserviceusingmessenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyService extends Service {

    private static final String TAG = "MyService";

    Messenger messenger = new Messenger(new IncomingHandler());
    static final int JOB_1 = 1;
    static final int JOB_2 = 2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(),"Service is Binding....",Toast.LENGTH_LONG).show();
        Log.d(TAG, "onBind: onBind is performed + messenger is bounded and sent back");
        Log.d(TAG, "onBind: going to service connection now");
        return messenger.getBinder();
    }

    static class IncomingHandler extends Handler{
        private ContextConnection contextConnection = new ContextConnection();

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "handleMessage: Message is received from MainActivity + checking cases now....");
            switch(msg.what){
                case JOB_1:
                    Toast.makeText(contextConnection.getMyContext(),"Hello from JOB 1 it is done",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "handleMessage: Hello from JOB 1 it is done");
                    break;
                case JOB_2:
                    Toast.makeText(contextConnection.getMyContext(),"Hello from JOB 2 it is done",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "handleMessage: Hello from JOB 2 it is done");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
