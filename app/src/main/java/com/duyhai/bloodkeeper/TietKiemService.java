package com.duyhai.bloodkeeper;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * Created by HaiTuan on 6/26/2015.
 */
public class TietKiemService extends Service {
    PendingIntent pendingIntent;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        push(getBaseContext(), intent);

        int idV = intent.getIntExtra("id", 0);
        Intent alarmIntent = new Intent(getBaseContext(), AlarmReceiver.class);
        alarmIntent.putExtra("id", idV);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        int interval = 3600000 * 24;
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + interval, interval, pendingIntent);
    }

    public void push(Context context, Intent intent) {
        String serName = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(serName);
        //khởi tạo các icon, thông báo  và thời gian để hiển thị cùng.
        int icon = R.drawable.ic_launcher;
        String tickerText = "Một thông báo mới.";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        String title = "BloodKeeper";
        String text = "Click xem chi tiết...";
        intent = new Intent(context, VayNoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent launchIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, 0);
        notification.setLatestEventInfo(context.getApplicationContext(), title, text, launchIntent);
        //kích hoạt notification
        int notificationId = 1;
        notificationManager.notify(notificationId, notification);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}