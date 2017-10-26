package com.example.kamhi.ex17;

import android.app.Service;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Kamhi on 3/10/2017.
 */

public class MyService extends Service {

    Worker myWorker;
    Date date;
    MyNotification notif;
    SimpleDateFormat fmt;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        myWorker = new Worker();
        myWorker.start();
        ;
        return super.onStartCommand(intent, flags, startId);
    }

    /*public String getHello() {
        return "Hello Service";
    }*/

    public String getCurrentTimeStamp(){
        return fmt.format(this.date);
    }

    public void showNotification(){
        this.notif = new MyNotification(this);
    }

    public void hideNotification(){
        if(this.notif!=null){
            this.notif.stop(MyNotification.NOTIF1);
            this.notif = null;
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                date = new Date();
                if (notif!=null){
                    notif.update(MyNotification.NOTIF1, getCurrentTimeStamp());
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    return;
                }
            }
        }
    }

        public class MyBinder extends Binder {
            public MyService getService() {
                return MyService.this;
            }
        }
    }

