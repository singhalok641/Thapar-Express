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
import com.exun.thaparexpress.model.BlogsList;

import java.util.List;

/**
 * Created by root on 28/11/15.
 */
public class CustomBlogListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<BlogsList> blogItems;

    public CustomBlogListAdapter(Activity activity, List<BlogsList> blogItems) {
        this.activity = activity;
        this.blogItems = blogItems;
    }

    @Override
    public int getCount() {
        return blogItems.size();
    }

    @Override
    public Object getItem(int location) {
        return blogItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_blogs, null);

        TextView blogName = (TextView) convertView.findViewById(R.id.blogTitle);

        // getting blog data for the row
        BlogsList m = blogItems.get(position);

        // title
        blogName.setText(m.getBlogName());

        return convertView;
    }

}
