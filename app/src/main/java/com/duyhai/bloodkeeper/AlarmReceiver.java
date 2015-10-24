package com.duyhai.bloodkeeper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.widget.Toast;

/**
 * Created by HaiTuan on 6/26/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        push(context, intent);
    }

    public void push(Context context, Intent intent) {
        int idV = intent.getIntExtra("id", 0);
        String serName = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(serName);
        //khởi tạo các icon, thông báo  và thời gian để hiển thị cùng.
        int icon = R.drawable.ic_launcher;
        String tickerText = "You have a message.";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        String title = "BloodKeeper";
        String text = "Click xem chi tiết. --" + idV;
        intent = new Intent(context, VayNoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent launchIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, 0);
        notification.setLatestEventInfo(context.getApplicationContext(), title, text, launchIntent);
        //kích hoạt notification
        int notificationId = 3;
        notificationManager.notify(notificationId, notification);
    }
}