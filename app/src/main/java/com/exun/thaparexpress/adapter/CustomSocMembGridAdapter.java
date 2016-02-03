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
import com.exun.thaparexpress.model.SocMembList;

import java.util.List;

/**
 * Created by n00b on 1/13/2016.
 */
public class CustomSocMembGridAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<SocMembList> socMembLists;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomSocMembGridAdapter(Activity activity, List<SocMembList> socMembLists) {
        this.activity = activity;
        this.socMembLists = socMembLists;
    }

    @Override
    public int getCount() {
        return socMembLists.size();
    }

    @Override
    public Object getItem(int position) {
        return socMembLists.get(position);
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
            convertView = inflater.inflate(R.layout.list_row_memb, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView membImage = (NetworkImageView) convertView
                .findViewById(R.id.memb_image);
        TextView name = (TextView) convertView.findViewById(R.id.memb_name);
        TextView desi = (TextView) convertView.findViewById(R.id.memb_desi);


        // getting socMembLists data for the row
        SocMembList m = socMembLists.get(position);

        membImage.setImageUrl(m.getImage(),imageLoader);

        // name
        name.setText(m.getName());

        // date
        desi.setText(m.getDesi());

        return convertView;
    }
}
