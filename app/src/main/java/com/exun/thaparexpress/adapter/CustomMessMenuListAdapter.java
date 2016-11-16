package com.exun.thaparexpress.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exun.thaparexpress.R;
import com.exun.thaparexpress.model.MessMenuList;

import java.util.List;

/**
 * Created by root on 11/15/16.
 */
public class CustomMessMenuListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<MessMenuList> MessMenuItems;

    public CustomMessMenuListAdapter(Activity activity, List<MessMenuList> MessMenuItems) {
        this.activity = activity;
        this.MessMenuItems = MessMenuItems;
    }

    @Override
    public int getCount() {
        return MessMenuItems.size();
    }

    @Override
    public Object getItem(int location) {
        return MessMenuItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_society, null);


        TextView title = (TextView) convertView.findViewById(R.id.title);

        // getting society data for the row
        MessMenuList m = MessMenuItems.get(position);

        // title
        //title.setText(m.getSocietyTitle());

        return convertView;

    }
}
