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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static double totalScore(List<Exam> exams) {
        double score = 0.0;
        for (int i = 0; i < exams.size(); i++) {
            score += exams.get(i).degree;
        }
        return score;
    }

    public static Integer getDaysNumberUntilNextExam(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);
        if (!prefs.contains(LVoc.NOTIFICATION_COUNT))
            prefs.edit().putInt(LVoc.NOTIFICATION_COUNT, 7).apply();
        return prefs.getInt(LVoc.NOTIFICATION_COUNT, 7);
    }


}
