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

public class NotificationHelper3 extends ContextWrapper {
    public static final String channelID = "evening aarti time";
    public static final String channelName = "its evening aari time";
    private NotificationManager mManager;
    public NotificationHelper3(Context base, Integer k) {
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
                .setContentTitle("Evening Aarti")
                .setContentText("It's Evening Aarti time.Click to listen Aarti.")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(resultPendingIntent);
    }
}
