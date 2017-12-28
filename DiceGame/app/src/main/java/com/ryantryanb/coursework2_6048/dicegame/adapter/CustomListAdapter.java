//*********************************
// CustomListAdapter.java
//*********************************
// Code created and maintained by:
// Ryan Biggs
//*********************************

package com.ryantryanb.coursework2_6048.dicegame.adapter;

import com.ryantryanb.coursework2_6048.dicegame.R;
import com.ryantryanb.coursework2_6048.dicegame.model.Scores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<Scores> scoresList;

    // Constructor
    public CustomListAdapter(Context context, List<Scores> scoresList)
    {
        this.mContext = context;
        this.scoresList = scoresList;
    }

    //******************
    // Getter for count
    //******************
    @Override
    public int getCount()
    {
        return scoresList.size();
    }

    //*****************
    // Getter for item
    //*****************
    @Override
    public Object getItem(int location)
    {
        return scoresList.get(location);
    }

    //********************
    // Getter for item id
    //********************
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    //********************************
    // //TODO: Biggs, describe method
    //********************************
    public void refreshList(List<Scores> scores)
    {
        this.scoresList.clear();             // TODO
        this.scoresList.addAll(scores);      // TODO
        notifyDataSetChanged();              // TODO
    }

    //********************************
    // //TODO: Biggs, describe method
    //********************************
    @Override
    public View getView(int position, View scoreView, ViewGroup parent)
    {
        ViewHolder holder;

        if (layoutInflater == null)                                                                           // TODO
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);     // TODO

        if (scoreView == null)                                                      // TODO
        {
            scoreView = layoutInflater.inflate(R.layout.list_row, parent, false);   // TODO
            holder = new ViewHolder();                                              // TODO
            holder.player = scoreView.findViewById(R.id.player);                    // TODO
            holder.score = scoreView.findViewById(R.id.score);                      // TODO

            scoreView.setTag(holder);                                               // TODO
        }

        else                                                                        // TODO
            holder = (ViewHolder) scoreView.getTag();                               // TODO

        final Scores m = scoresList.get(position);                                  // TODO

        holder.player.setText(m.getPlayer());                                       // TODO
        holder.score.setText(String.valueOf(m.getScore()));                         // TODO

        return scoreView;                                                           // TODO
    }

    //********************************
    // //TODO: Biggs, describe method
    //********************************
    static class ViewHolder
    {
        TextView player;
        TextView score;
    }
}



