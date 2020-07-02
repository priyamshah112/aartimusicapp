package com.example.letsplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import android.util.Log;


public class AlertReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Integer k =0;

        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if(k==0)
                k= (Integer) value;
        }

        if(k==2) {
            NotificationHelper2 notificationHelper = new NotificationHelper2(context, k);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification(k);
            notificationHelper.getManager().notify(1, nb.build());
        }
    }
}
