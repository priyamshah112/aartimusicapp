package com.example.letsplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
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
        }
        if(k==1) {
            NotificationHelper notificationHelper = new NotificationHelper(context, k);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification(k);
            notificationHelper.getManager().notify(1, nb.build());
        }else  if(k==2) {
            NotificationHelper2 notificationHelper = new NotificationHelper2(context, k);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification(k);
            notificationHelper.getManager().notify(1, nb.build());
        }else if (k == 3) {
            NotificationHelper3 notificationHelper = new NotificationHelper3(context, k);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification(k);
            notificationHelper.getManager().notify(1, nb.build());
        }
    }
}

//future use

//public class AlertReceiver extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Bundle bundle = intent.getExtras();
//        Integer k =0;
//        for (String key : bundle.keySet()) {
//            Object value = bundle.get(key);
//            if(k==0)
//                k= (Integer) value;
//            //Log.d("chala ja b hai ", String.format("%s %s (%s)", key,
//            //        value.toString(), value.getClass().getName()));
//        }
//        Log.w("got the id "+k,"finally got the message");
//        NotificationHelper notificationHelper = new NotificationHelper(context,k);
//        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(k);
//        if(k==1) {
//            notificationHelper.getManager().notify(1, nb.build());
//        }else if(k==2) {
//            notificationHelper.getManager().notify(2, nb.build());
//        }else if(k==3) {
//            notificationHelper.getManager().notify(3, nb.build());
//        }
//    }
//}
