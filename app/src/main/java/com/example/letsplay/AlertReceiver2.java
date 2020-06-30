package com.example.letsplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class AlertReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Integer k =0;
        Log.w("in brach 1","in the recier 2");
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if(k==0)
                k= (Integer) value;
            Log.d("chala ja b hai ", String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }
        if(k==2) {
            Log.w("got the id " + k, "finally got the message");
            NotificationHelper2 notificationHelper = new NotificationHelper2(context, k);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification(k);
            //notificationHelper.getManager().notify(2, nb.build());
            notificationHelper.getManager().notify(1, nb.build());
        }
    }
}
