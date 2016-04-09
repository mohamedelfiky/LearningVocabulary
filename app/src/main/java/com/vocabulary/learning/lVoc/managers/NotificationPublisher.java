package com.vocabulary.learning.lVoc.managers;
/**
 * Created by mohamed on 03/04/16.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.vocabulary.learning.lVoc.models.Dictionary;
import com.vocabulary.learning.lVoc.utils.LVoc;

public class NotificationPublisher extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int id = intent.getIntExtra(LVoc.NOTIFICATION_ID, 0);
        if (id != 0) {
            Notification notification = intent.getParcelableExtra(LVoc.NOTIFICATION);
            SharedPreferences prefs = context.getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);

            int notificationsNumber = prefs.getInt(LVoc.NOTIFICATION_COUNT, 7);
            if (notificationsNumber > 1) {
                // decrease days to next exam
                prefs.edit().putInt(LVoc.NOTIFICATION_COUNT, notificationsNumber - 1).apply();

                // set current to exam
                String currentVocabulary = prefs.getString(LVoc.CURRENT_VOCABULARY, ",");
                Boolean isNewExam = (notificationsNumber == 7);
                Dictionary.setExamVocabularies(context, isNewExam, currentVocabulary.split(","));
            } else {
                //get exam notification
                notification = new LVocNotification(context).getNotification(true);
            }

            // set next vocabulary to current one
            prefs.edit().putString(LVoc.CURRENT_VOCABULARY, prefs.getString(LVoc.NEXT_VOCABULARY, "")).apply();

            // trigger notification
            notificationManager.notify(id, notification);
        }
        // schedule a new notification
        new LVocNotification(context).schedule(true);
    }
}
