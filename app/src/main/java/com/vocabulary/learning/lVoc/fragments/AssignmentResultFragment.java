package com.vocabulary.learning.lVoc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.models.Exam;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentResultFragment extends Fragment {
    @Bind(R.id.circularProgressbar)
    ProgressBar circularProgressbar;
    @Bind(R.id.txtExamResult)
    TextView txtExamResult;

    public AssignmentResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_result, container, false);
        ButterKnife.bind(this, view);
        loadView();
        return view;
    }

    private void loadView() {
        AssignmentActivity baseActivity = ((AssignmentActivity) getActivity());
        circularProgressbar.setProgress(baseActivity.getScore());
        double percentage = (((double) baseActivity.getScore()) / Exam.FULL_MARK) * 100;
        txtExamResult.setText(String.format("%s%%", String.format("%1$.2f ", percentage)));
    }
}
