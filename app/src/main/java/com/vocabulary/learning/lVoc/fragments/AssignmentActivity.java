package com.vocabulary.learning.lVoc.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.models.Dictionary;
import com.vocabulary.learning.lVoc.models.Exam;
import com.vocabulary.learning.lVoc.utils.LVoc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AssignmentActivity extends AppCompatActivity implements View.OnClickListener {
    public List<Integer> results = new ArrayList<>();
    public List<String> loadedQuestions = new ArrayList<>();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.nextQuestion)
    Button nextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        ButterKnife.bind(this);

        nextQuestion.setOnClickListener(this);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getText(R.string.title_activity_assignment));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextQuestion:
                nextQuestionClick();
                break;
        }
    }

    private void nextQuestionClick() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (currentFragment instanceof AssignmentActivityFragment) {
            Boolean isTrueAnswer = ((AssignmentActivityFragment) currentFragment).getQuestionResult();
            if (isTrueAnswer != null) {
                results.add((isTrueAnswer) ? 1 : 0);

                if (results.size() < 7) {
                    // load new fragment question
                    goToFragment(new AssignmentActivityFragment());
                } else {
                    nextQuestion.setText(getResources().getText(R.string.btn_close));
                    new Exam(new Date(), getScore()).save();
                    goToFragment(new AssignmentResultFragment());

                    SharedPreferences prefs = getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);
                    prefs.edit().putInt(LVoc.NOTIFICATION_COUNT, 7).apply();
                }
            }
        } else if (currentFragment instanceof AssignmentResultFragment) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void goToFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, newFragment).commit();
    }


    public Integer getScore() {
        Integer sum = 0;
        for (int i = 0; i < results.size(); i++) {
            sum += results.get(i);
        }
        return sum * 10;
    }

    public Dictionary getVocabulary() {
        String id=Dictionary.pickExamVocabulary(this);
        loadedQuestions.add(id);
        return Dictionary.findVocabularyWithAnswers(id, TextUtils.join(",", loadedQuestions));
    }
}
