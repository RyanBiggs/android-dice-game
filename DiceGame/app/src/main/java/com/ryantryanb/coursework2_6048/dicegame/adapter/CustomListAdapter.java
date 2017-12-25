package com.ryantryanb.coursework2_6048.dicegame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ryantryanb.coursework2_6048.dicegame.R;
import com.ryantryanb.coursework2_6048.dicegame.model.Scores;

import java.util.List;

/**
 * Custom List Adapter for GameActivity List View
 */



public class CustomListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<Scores> scoresScores;



    public CustomListAdapter(Context context, List<Scores> scoresScores) {
        this.mContext = context;
        this.scoresScores = scoresScores;

    }

    @Override
    public int getCount() {
        return scoresScores.size();
    }

    @Override
    public Object getItem(int location) {
        return scoresScores.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void refreshList(List<Scores> scoresScores) {
        this.scoresScores.clear();
        this.scoresScores.addAll(scoresScores);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View scoreView, ViewGroup parent) {
        ViewHolder holder;
        if (inflater == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (scoreView == null) {

            scoreView = inflater.inflate(R.layout.list_row, parent, false);
            holder = new ViewHolder();
            holder.player = (TextView) scoreView.findViewById(R.id.player);
            holder.score = (TextView) scoreView.findViewById(R.id.score);

            scoreView.setTag(holder);

        } else {
            holder = (ViewHolder) scoreView.getTag();
        }

        final Scores m = scoresScores.get(position);
        holder.player.setText(m.getPlayer());
        holder.score.setText(String.valueOf(m.getScore()));

        return scoreView;
    }



    static class ViewHolder {

        TextView player;
        TextView score;

    }

}



