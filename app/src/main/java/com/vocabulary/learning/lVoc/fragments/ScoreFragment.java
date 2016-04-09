package com.vocabulary.learning.lVoc.fragments;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.managers.NotificationPublisher;
import com.vocabulary.learning.lVoc.models.Exam;
import com.vocabulary.learning.lVoc.utils.LVoc;
import com.vocabulary.learning.lVoc.views.adapters.ScoreResultsRecyclerAdapter;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {

    @Bind(R.id.totalScore)
    TextView totalScore;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    @Bind(R.id.txtNextExam)
    TextView txtNextExam;

    public ScoreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        ButterKnife.bind(this, view);
        loadView();
        return view;
    }

    private void loadView() {
        List<Exam> all = Exam.findAll();
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(new ScoreResultsRecyclerAdapter(all));
        totalScore.setText(String.format("%1$.0f", Exam.totalScore(all)));
        Integer after_days = Exam.getDaysNumberUntilNextExam(getActivity());
        txtNextExam.setText(String.format(getResources().getString(R.string.next_exam_date), after_days));
    }
}
