package com.vocabulary.learning.lVoc.managers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vocabulary.learning.lVoc.models.Dictionary;
import com.vocabulary.learning.lVoc.utils.LVoc;

public class NotificationPublisher extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getIntExtra(LVoc.NOTIFICATION_ID, 0);
        if (id != 0) {
            Notification notification = intent.getParcelableExtra(LVoc.NOTIFICATION);
            notificationManager.notify(id, notification);
        } else {
            new LVocNotification(context).schedule(Dictionary.getWord().toString());
        }
    }
}
