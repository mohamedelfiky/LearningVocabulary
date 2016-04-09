package com.vocabulary.learning.lVoc.managers;
/**
 * Created by elfiky on 08/03/15.
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.fragments.MainActivity;
import com.vocabulary.learning.lVoc.models.Dictionary;
import com.vocabulary.learning.lVoc.utils.LVoc;

import java.util.List;

import android.text.TextUtils;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class LVocNotification {
    Context context;

    public LVocNotification(Context context) {
        this.context = context;
    }

    public void schedule(Boolean force) {
        SharedPreferences prefs = context.getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);
        if (force || !prefs.getBoolean(LVoc.IS_SCHEDULED, false)) {
            prefs.edit().putBoolean(LVoc.IS_SCHEDULED, true).apply();

            Intent notificationIntent = new Intent(context, NotificationPublisher.class);
            notificationIntent.putExtra(LVoc.NOTIFICATION_ID, 1);
            notificationIntent.putExtra(LVoc.NOTIFICATION, getNotification());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//            long futureInMillis = SystemClock.elapsedRealtime() + (1000 * 10);
             long futureInMillis = SystemClock.elapsedRealtime() + getDelay();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        }
    }

    public void schedule() {
        schedule(false);
    }


    /**
     * save next two vocabularies in SharedPreferences
     *
     * @return first vocabulary as a Notification Title
     */
    public String getNotificationTitle() {

        SharedPreferences prefs = context.getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);

        List<Dictionary> dic = Dictionary.findVocabulariesWithExcludes(TextUtils.join(",", Dictionary.getExamVocabularies(context)), 2);
        prefs.edit().putString(LVoc.NEXT_VOCABULARY, dic.get(0).getId() + "," + dic.get(1).getId()).apply();

        return dic.get(0).toString();
    }

    public Notification getNotification(Boolean forExam) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent i = PendingIntent.getActivity(context, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(context.getText(R.string.app_name));
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentText((forExam) ? context.getString(R.string.exam_notification) : getNotificationTitle());
        builder.setContentIntent(i);
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setSmallIcon(R.drawable.ic_action_star);
        return builder.build();
    }

    public Notification getNotification() {
        return getNotification(false);
    }

    // get delay from now to next 8am
    public Integer getDelay() {
        DateTime today = new DateTime();
        today = today.withSecondOfMinute(0);
        today = today.withMillisOfSecond(0);
        today = today.withMinuteOfHour(0);
        int h = today.getHourOfDay();
        if (h >= 8) {
            // set to tomorrow 8am
            today = today.plusDays(1).withHourOfDay(8);
        } else {
            // set to today 8am
            today = today.withHourOfDay(8);
        }
        return Math.abs(Seconds.secondsBetween(new DateTime(), today).getSeconds()) * 1000;
    }
}