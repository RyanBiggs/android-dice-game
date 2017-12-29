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
    // Creates each ListView row
    //********************************
    @Override
    public View getView(int position, View scoreView, ViewGroup parent)
    {
        ViewHolder holder;

        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (scoreView == null)
        {
            scoreView = layoutInflater.inflate(R.layout.list_row, parent, false);   // Inflate list_row.xml file for each row
            holder = new ViewHolder();                                              // View Holder Object to contain list_row.xml file elements
            holder.player = scoreView.findViewById(R.id.player);
            holder.score = scoreView.findViewById(R.id.score);

            scoreView.setTag(holder);                                               // Set holder with LayoutInflater
        }

        else
            holder = (ViewHolder) scoreView.getTag();

        final Scores m = scoresList.get(position);

        holder.player.setText(m.getPlayer());
        holder.score.setText(String.valueOf(m.getScore()));

        return scoreView;
    }

    //********************************
    // Create a holder class to contain inflated xml elements
    //********************************
    static class ViewHolder
    {
        TextView player;
        TextView score;
    }
}



