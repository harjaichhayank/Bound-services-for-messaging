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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
            messenger2 = new Messenger(service);
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger2 = null;
            isBind = false;
        }
    };

    @Override
    protected void onStop() {
        unbindService(serviceConnection);
        isBind = false;
        messenger2 = null;
        super.onStop();
    }

    public void bindService(View view) {

        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void sayHello(View view) {
        if (isBind) {
            String button_text;
            button_text = (String) ((Button) view).getText();
            if (button_text.equals("Say Hello")) {
                Message message = Message.obtain(null, MyService.JOB_1, 0, 0, 0);
                try {
                    messenger2.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (button_text.equals("Say Hello Again")) {
                Message message = Message.obtain(null, MyService.JOB_2, 0, 0, 0);
                try {
                    messenger2.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                }
            }
        else {
                Toast.makeText(getApplicationContext(), "Bind Service first", Toast.LENGTH_SHORT).show();
            }
        }
    }
