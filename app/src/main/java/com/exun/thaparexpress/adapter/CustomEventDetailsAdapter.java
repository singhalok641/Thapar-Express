package com.exun.thaparexpress.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exun.thaparexpress.R;
import com.exun.thaparexpress.model.EventsDetailsList;

import java.util.List;

/**
 * Created by root on 29/11/15.
 */
public class CustomEventDetailsAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<EventsDetailsList> eventsItems;

    public CustomEventDetailsAdapter(Activity activity, List<EventsDetailsList> eventsItems) {
        this.activity = activity;
        this.eventsItems = eventsItems;
    }

    @Override
    public int getCount() {
        return eventsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return eventsItems.get(position);
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
            convertView = inflater.inflate(R.layout.list_row_event_details, null);

        TextView head = (TextView) convertView.findViewById(R.id.detailHead);
        TextView text = (TextView) convertView.findViewById(R.id.detailText);

        // getting events data for the row
        EventsDetailsList m = eventsItems.get(position);


        // name
        head.setText(m.getDetailHead());

        // date
        text.setText(m.getDetailText());

        return convertView;
    }
}
