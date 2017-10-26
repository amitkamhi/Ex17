package com.example.kamhi.ex17;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Kamhi on 18/10/2017.
 */

public class MyNotification {

    final int PENDING_REQUEST = 0;
    Notification.Builder builder;
    static NotificationManager notiManager = null;
    public final static int NOTIF1 = 1;

    MyNotification(Context context){
        Intent myIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, PENDING_REQUEST, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder = new Notification.Builder(context);
        builder.setTicker("My Service")
                .setContentTitle("New Info")
                .setSmallIcon(R.drawable.clock)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(false);
        if(notiManager == null){
            notiManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    public void update(int notificationId, String message){
        builder.setContentText(message).setOnlyAlertOnce(false);
        Notification noti = builder.build();
        noti.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        notiManager.notify(notificationId, noti);
    }

    public void stop(int notificationId){
        notiManager.cancel(notificationId);
    }
}
