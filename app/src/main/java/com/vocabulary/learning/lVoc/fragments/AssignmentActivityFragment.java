package com.vocabulary.learning.lVoc.fragments;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.models.Dictionary;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class AssignmentActivityFragment extends Fragment {

    @Bind(R.id.txtQuestion)
    TextView txtQuestion;
    @Bind(R.id.answer_1)
    RadioButton answer_1;
    @Bind(R.id.answer_2)
    RadioButton answer_2;
    @Bind(R.id.answer_3)
    RadioButton answer_3;
    @Bind(R.id.answer_4)
    RadioButton answer_4;
    @Bind(R.id.grpAnswers)
    RadioGroup grpAnswers;
    @Bind(R.id.txtQuestionNo)
    TextView txtQuestionNo;
    Dictionary dic;
    View view;

    public AssignmentActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment, container, false);
        ButterKnife.bind(this, view);
        loadView();
        return view;
    }

    private void loadView() {
        dic = Dictionary.getQuestionWithAnswers("");
        txtQuestion.setText(dic.ar);
        answer_1.setText(dic.answers.get(0));
        answer_2.setText(dic.answers.get(1));
        answer_3.setText(dic.answers.get(2));
        answer_4.setText(dic.answers.get(3));
        txtQuestionNo.setText((((AssignmentActivity) getActivity()).results.size() + 1) + "/7");
    }

    public Boolean getQuestionResult() {
        int radioButtonID = grpAnswers.getCheckedRadioButtonId();
        if (radioButtonID == -1) {
            //no item selected alert
            if (getView() != null)
                Snackbar.make(getView(), "Please Answer the question above !!", Snackbar.LENGTH_LONG).show();
            return null;
        } else {
            RadioButton selected = (RadioButton) view.findViewById(radioButtonID);
            return selected.getText() == dic.en;
        }
    }
}
