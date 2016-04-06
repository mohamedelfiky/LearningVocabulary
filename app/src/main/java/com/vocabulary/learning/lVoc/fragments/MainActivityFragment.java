package com.vocabulary.learning.lVoc.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.models.Dictionary;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment {

    @Bind(R.id.txtVoc1)
    TextView txtVoc1;
    @Bind(R.id.txtVoc2)
    TextView txtVoc2;


    public MainActivityFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        loadView();
        return view;
    }

    private void loadView() {
        List<Dictionary> dic = Dictionary.getTwo();
        if (dic.size() == 2) {
            txtVoc1.setText(dic.get(0).en + " : " + dic.get(0).ar);
            txtVoc2.setText(dic.get(1).en + " : " + dic.get(1).ar);
        }
    }
}
