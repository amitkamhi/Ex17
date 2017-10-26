package com.example.kamhi.ex17;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    private MyService myService;
    private Thread monitor = null;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                myService = ((MyService.MyBinder)iBinder).getService();
                myService.hideNotification();
                //Toast.makeText(MainActivity.this, myService.getHello(),Toast.LENGTH_LONG).show();
                monitor = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                final String time = myService.getCurrentTimeStamp();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TextView textView = (TextView)findViewById(R.id.textView);
                                        textView.setText(time);
                                    }
                                });
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                break;
                            }
                        }
                    }
                });
                monitor.start();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                if(isChecked){
                    if (!isMyServiceRunning(MyService.class)){
                        startService(intent);
                    }
                    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                }
                else{
                    unbindService(serviceConnection);
                    stopService(intent);
                    if(monitor!=null){
                        monitor.interrupt();
                        monitor = null;
                    }
                }
            }
        });
        if (isMyServiceRunning(MyService.class)){
            if (tb.isChecked()){
                bindService(new Intent(MainActivity.this, MyService.class), serviceConnection, Context.BIND_AUTO_CREATE);
            }
            else {
                tb.setChecked(true);
            }
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(myService!=null){
            myService.showNotification();
        }
        if(monitor!=null){
            monitor.interrupt();
            monitor = null;
        }
    }



    private boolean isMyServiceRunning(Class<?> service){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
