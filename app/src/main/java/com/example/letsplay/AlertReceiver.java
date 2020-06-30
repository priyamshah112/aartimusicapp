package com.example.letsplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Integer k =0;
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if(k==0)
                k= (Integer) value;
            //Log.d("chala ja b hai ", String.format("%s %s (%s)", key,
            //        value.toString(), value.getClass().getName()));
        }
        Log.w("got the id "+k,"finally got the message");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(k);
        notificationHelper.getManager().notify(1, nb.build());
    }
}
