package com.example.boundserviceusingmessenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Messenger messenger2 = null;
    boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: should have been called right after onBind: going to service connection");
            messenger2 = new Messenger(service);
            isBind = true;
            Log.d(TAG, "onServiceConnected: Service is connected");
            Log.d(TAG, "onServiceConnected: waiting for the next action now");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: When am i called?");
            messenger2 = null;
            isBind = false;
        }
    };

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: Activity is being stopped");
        unbindService(serviceConnection);
        isBind = false;
        messenger2 = null;
        super.onStop();
    }

    public void bindService(View view) {
        Intent intent = new Intent(this, MyService.class);
        Log.d(TAG, "bindService: Intent is called");
        Log.d(TAG, "bindService: bind service will be called and we go to MyService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "bindService: going to onBind now");
    }

    public void sayHello(View view) {
        if (isBind) {
            String button_text;
            button_text = ((Button) view).getText().toString();
            if (button_text.equals("Say Hello")) {
                Log.d(TAG, "sayHello: Message is being obtained");
                Message message = Message.obtain(null, MyService.JOB_1,0,0,0);
                Log.d(TAG, "sayHello: Message is obtained");
                try {
                    messenger2.send(message);
                    Log.d(TAG, "sayHello: Message is send to Incoming Handler in MyService");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (button_text.equals("Say Hello Again")) {
                Log.d(TAG, "sayHello: Message is being obtained");
                Message message = Message.obtain(null, MyService.JOB_2,0,0,0);
                Log.d(TAG, "sayHello: Message is obtained");
                try {
                    messenger2.send(message);
                    Log.d(TAG, "sayHello: Message is send to Incoming Handler in MyService");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                }
            }
        else {
            Toast.makeText(getApplicationContext(), "Bind Service first", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "bind First: ");
        }
    }
}
