package com.vocabulary.learning.lVoc.fragments;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.models.Exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AssignmentActivity extends AppCompatActivity implements View.OnClickListener {
    public List<Integer> results = new ArrayList<>();

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
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment);
                if (f instanceof AssignmentActivityFragment) {
                    Boolean b = ((AssignmentActivityFragment) f).getQuestionResult();
                    if (b != null) {
                        results.add((b) ? 1 : 0);


                        if (results.size() < 7) {
                            goToFragment(new AssignmentActivityFragment());
                        } else {
                            nextQuestion.setText(getResources().getText(R.string.btn_close));
                            new Exam(new Date(), getScore()).save();
                            goToFragment(new AssignmentResultFragment());
                        }
                    }
                } else if (f instanceof AssignmentResultFragment) {
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
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
}
