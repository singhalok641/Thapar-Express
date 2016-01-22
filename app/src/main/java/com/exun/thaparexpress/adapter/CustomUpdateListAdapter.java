package com.exun.thaparexpress.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exun.thaparexpress.R;
import com.exun.thaparexpress.model.UpdatesList;

import java.util.List;

/**
 * Created by root on 28/11/15.
 */
public class CustomUpdateListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<UpdatesList> updatesLists;

    public CustomUpdateListAdapter(Activity activity, List<UpdatesList> updatesLists) {
        this.activity = activity;
        this.updatesLists = updatesLists;
    }

    @Override
    public int getCount() {
        return updatesLists.size();
    }

    @Override
    public Object getItem(int location) {
        return updatesLists.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_updates, null);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView message = (TextView) convertView.findViewById(R.id.message);
        int color;

        // getting society data for the row
        UpdatesList m = updatesLists.get(position);

        // random color
        color = m.getRandomColor();

        // title
        title.setText(m.getHead());

        //message
        message.setText(m.getUpdate());

        //color
        title.setTextColor(color);
        message.setTextColor(color);

        return convertView;
    }

}
