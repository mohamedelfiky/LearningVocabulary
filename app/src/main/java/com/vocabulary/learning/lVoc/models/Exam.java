package com.vocabulary.learning.lVoc.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.vocabulary.learning.lVoc.utils.LVoc;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;
import java.util.List;

/**
 * Created by mohamed on 03/04/16.
 */
@Table(name = "Exams")
public class Exam extends Model {
    public static final double FULL_MARK = 70.0;
    @Column(name = "Date")
    public Date date;

    @Column(name = "Degree")
    public double degree;


    public Exam() {
    }

    public Exam(Date date, double degree) {
        this.degree = degree;
        this.date = date;
    }

    public static List<Exam> findAll() {
        return new Select().from(Exam.class).execute();
    }

    public static double totalScore(List<Exam> all) {
        double res = 0.0;
        for (int i = 0; i < all.size(); i++) {
            res += all.get(i).degree;
        }
        return res;
    }

    public Integer getNextExamDays() {
        DateTime sevenDaysLater = new DateTime(date).plusDays(7);
        return Days.daysBetween(new DateTime().toLocalDate(), sevenDaysLater.toLocalDate()).getDays();
    }


    public static Integer getDaysNumberUntilNextExam(Context context, List<Exam> all) {
        Integer after_days;
        if (all.size() > 0) {
            after_days = all.get(all.size() - 1).getNextExamDays();
        } else {
            SharedPreferences prefs = context.getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);
            if (prefs.contains(LVoc.FIRST_DAY_KEY)) {
                after_days = Days.daysBetween(new DateTime().toLocalDate(), new DateTime(new Date(prefs.getLong(LVoc.FIRST_DAY_KEY, 0))).toLocalDate()).getDays();
            } else {
                Date date = new Date(System.currentTimeMillis());
                prefs.edit().putLong(LVoc.FIRST_DAY_KEY, date.getTime()).apply();
                after_days = 7;
            }
        }
        return after_days;
    }

}
