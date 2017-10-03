package com.example.kamhi.ex17;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                if(isChecked){
                    if (!isMyServiceRunning(MyService.class)){
                        startService(intent);
                    }
                }
                else{
                    stopService(intent);
                }
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> service){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (".MyService".equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
