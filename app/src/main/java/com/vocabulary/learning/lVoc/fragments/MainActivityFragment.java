package com.vocabulary.learning.lVoc.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.models.Dictionary;
import com.vocabulary.learning.lVoc.utils.LVoc;

import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment {

    @Bind(R.id.txtVoc1_ar)
    TextView txtVoc1Ar;
    @Bind(R.id.txtVoc1_en)
    TextView txtVoc1En;
    @Bind(R.id.txtVoc2_ar)
    TextView txtVoc2Ar;
    @Bind(R.id.txtVoc2_en)
    TextView txtVoc2En;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        loadView();
        return view;
    }

    private void loadView() {
        SharedPreferences prefs = getActivity().getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);
        String current_ids = prefs.getString(LVoc.CURRENT_VOCABULARY, "");
        if (current_ids.equals(""))
            current_ids = "1,2";
        List<Dictionary> dic = Dictionary.findVocabulariesByIds(current_ids);
        if (dic.size() == 2) {
            txtVoc1Ar.setText(dic.get(0).ar);
            txtVoc1En.setText(dic.get(0).en);
            txtVoc2Ar.setText(dic.get(1).ar);
            txtVoc2En.setText(dic.get(1).en);
            if (!Objects.equals(current_ids, ""))
                prefs.edit().putString(LVoc.CURRENT_VOCABULARY, dic.get(0).getId() + "," + dic.get(1).getId()).apply();
        }
    }
}
