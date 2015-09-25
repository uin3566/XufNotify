package com.roubow.xufnotify.components;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.roubow.xufnotify.data.EventBean;

/**
 * Created by lenov0 on 2015/9/19.
 */
public class BackgroundService extends Service {

    private MyBinder mBinder = new MyBinder();

    private AlarmManager mAlarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {

        public void setAlarm(EventBean bean){
            Intent intent = new Intent(BackgroundService.this, NotifyReceiver.class);
            intent.putExtra(EventDetailActivity.EXTRA_CONTENT, bean.getEventContent());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(BackgroundService.this, 0, intent, 0);
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, bean.getDoEventDate().getTime(), pendingIntent);
        }
    }

}
