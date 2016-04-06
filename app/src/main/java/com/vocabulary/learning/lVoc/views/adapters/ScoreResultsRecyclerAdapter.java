package com.vocabulary.learning.lVoc.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vocabulary.learning.lVoc.R;
import com.vocabulary.learning.lVoc.models.Exam;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by mohamed on 29/03/16.
 */
public class ScoreResultsRecyclerAdapter extends RecyclerView.Adapter<ScoreResultsRecyclerAdapter.ViewHolder> {

    private List<Exam> mItems;

    public ScoreResultsRecyclerAdapter(List<Exam> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.score_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Exam item = mItems.get(i);
        viewHolder.txtExamDate.setText("إمتحان " + new SimpleDateFormat("yyyy-MM-dd").format(item.date));
        viewHolder.txtResult.setText(String.format("%1$.0f / 70", item.degree));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtExamDate, txtResult;

        ViewHolder(View v) {
            super(v);
            txtExamDate = (TextView) v.findViewById(R.id.txtExamDate);
            txtResult = (TextView) v.findViewById(R.id.txtResult);
        }
    }

}
