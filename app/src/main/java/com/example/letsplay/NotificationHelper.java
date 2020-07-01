package com.example.letsplay;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "Morning aarti";
    public static final String channelName = "its morning aarti time";
    private NotificationManager mManager;
    public NotificationHelper(Context base,Integer k) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(k);
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel(Integer k) {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification(Integer k) {

        //Log.w("got the id in notifi "+k,"finally got the message in the class");
        Intent resultIntent = new Intent(this, SingleMusicPlayer.class);
        String aString = Integer.toString(k);
        resultIntent.putExtra("id",k);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Morning Aarti")
                .setContentText("It's Morning Aarti time.Click to listen Aarti.")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(resultPendingIntent);
    }
}

//
//package com.example.letsplay;
//
//import android.annotation.TargetApi;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
//import android.content.Context;
//import android.content.ContextWrapper;
//import android.content.Intent;
//import android.os.Build;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//public class NotificationHelper extends ContextWrapper {
//    public static final String channelID = "Morning";
//    public static final String channelName = "Morning Alarm";
//    public static final String channelID3 = "Evening";
//    public static final String channelName3 = "Evening Alarm";
//    public static final String channelID2 = "Afternoon";
//    public static final String channelName2 = "Afternoon Alarm";
//    private NotificationManager mManager;
//    public NotificationHelper(Context base,Integer k) {
//        super(base);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createChannel(k);
//        }
//    }
//    @TargetApi(Build.VERSION_CODES.O)
//    private void createChannel(Integer k) {
//        if(k==1) {
//            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
//            getManager().createNotificationChannel(channel);
//        }else if(k==2) {
//            NotificationChannel channel = new NotificationChannel(channelID2, channelName2, NotificationManager.IMPORTANCE_HIGH);
//            getManager().createNotificationChannel(channel);
//        }else if(k==3) {
//            NotificationChannel channel = new NotificationChannel(channelID3, channelName3, NotificationManager.IMPORTANCE_HIGH);
//            getManager().createNotificationChannel(channel);
//        }
//
//    }
//    public NotificationManager getManager() {
//        if (mManager == null) {
//            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        }
//        return mManager;
//    }
//    public NotificationCompat.Builder getChannelNotification(Integer k) {
//
//        //Log.w("got the id in notifi "+k,"finally got the message in the class");
//        Intent resultIntent = new Intent(this, SingleMusicPlayer.class);
//        //String aString = Integer.toString(k);
//        resultIntent.putExtra("id",k);
//        // Create the TaskStackBuilder and add the intent, which inflates the back stack
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(resultIntent);
//        // Get the PendingIntent containing the entire back stack
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        if(k==1){
//            return new NotificationCompat.Builder(getApplicationContext(), channelID)
//                    .setContentTitle("Morning Aarti")
//                    .setContentText("Morning Aarti time.")
//                    .setSmallIcon(R.drawable.ic_launcher_background)
//                    .setContentIntent(resultPendingIntent);
//        }
//        else if(k==2){
//            return new NotificationCompat.Builder(getApplicationContext(), channelID2)
//                    .setContentTitle("Afernoon Aarti")
//                    .setContentText("Afternoon Aarti time.")
//                    .setSmallIcon(R.drawable.ic_launcher_background)
//                    .setContentIntent(resultPendingIntent);
//        }
//        else if(k==3) {
//            return new NotificationCompat.Builder(getApplicationContext(), channelID3)
//                    .setContentTitle("Evevning Aarti")
//                    .setContentText("Evening Aarti time.")
//                    .setSmallIcon(R.drawable.ic_launcher_background)
//                    .setContentIntent(resultPendingIntent);
//        }
//        return null;
//    }
//}
//
