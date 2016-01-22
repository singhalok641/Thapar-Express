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
import com.exun.thaparexpress.model.SocietyList;

import java.util.List;

/**
 * Created by root on 28/11/15.
 */
public class CustomSocietyListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<SocietyList> societyItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomSocietyListAdapter(Activity activity, List<SocietyList> societyItems) {
        this.activity = activity;
        this.societyItems = societyItems;
    }

    @Override
    public int getCount() {
        return societyItems.size();
    }

    @Override
    public Object getItem(int location) {
        return societyItems.get(location);
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

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);

        // getting society data for the row
        SocietyList m = societyItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getSocietyTitle());

        return convertView;
    }

}
