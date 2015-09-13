package com.roubow.xufnotify.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.roubow.xufnotify.R;

/**
 * Created by lenov0 on 2015/9/13.
 */
public class NotifyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String content = intent.getStringExtra(EventDetailActivity.EXTRA_CONTENT);

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);

        Notification notification = new Notification(R.mipmap.ic_launcher, content, System.currentTimeMillis());
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.ledARGB = 0x00ff00;
        notification.ledOnMS = 1000;
        notification.ledOnMS = 1000;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.setLatestEventInfo(context, "备忘提醒", content, pi);
        manager.notify(100, notification);
    }
}
