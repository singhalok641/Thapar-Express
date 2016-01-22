package com.exun.thaparexpress.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.model.EventsList;

import java.util.List;

/**
 * Created by root on 29/11/15.
 */
public class CustomEventListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<EventsList> eventsItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomEventListAdapter(Activity activity, List<EventsList> eventsItems) {
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
            convertView = inflater.inflate(R.layout.list_row_event, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView eventImage = (NetworkImageView) convertView
                .findViewById(R.id.eventImage);
        TextView date = (TextView) convertView.findViewById(R.id.eventDate);
        TextView name = (TextView) convertView.findViewById(R.id.eventTitle);
        TextView shortDesc = (TextView) convertView.findViewById(R.id.eventShortDesc);

        String sShortDesc;

        // getting events data for the row
        EventsList m = eventsItems.get(position);

        // Event image
        eventImage.setImageUrl(m.getEventImage(), imageLoader);

        // name
        name.setText(m.getEventName());

        // date
        date.setText(m.getDate());

        //Short Description
        sShortDesc = m.getEventShortDesc();

        if (sShortDesc.equals(" "))
        {
            shortDesc.setVisibility(View.INVISIBLE);
        }
        else {
            shortDesc.setText(sShortDesc);
        }

        return convertView;
    }
}
