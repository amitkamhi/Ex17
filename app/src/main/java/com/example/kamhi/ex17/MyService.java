package com.example.kamhi.ex17;

import android.app.Service;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.IBinder;

/**
 * Created by Kamhi on 3/10/2017.
 */

public class MyService extends Service {

    Worker myWorker;
    Date date;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myWorker = new Worker();
        myWorker.start();;
        return super.onStartCommand(intent, flags, startId);
    }

    private class Worker extends Thread{

        @Override
        public void run() {
            super.run();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            while (true){
                date = new Date();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    return;
                }
            }
        }
    }
}
