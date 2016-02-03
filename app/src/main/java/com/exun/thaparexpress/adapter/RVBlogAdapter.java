package com.exun.thaparexpress.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exun.thaparexpress.R;
import com.exun.thaparexpress.model.BlogsList;

import java.util.List;

/**
 * Created by root on 3/2/16.
 */
public class RVBlogAdapter extends RecyclerView.Adapter<RVBlogAdapter.DataHolderObject> {

    private static String LOG_TAG = "RVBlogAdapter";
    private List<BlogsList> blogItems;
    private static MyClickListener myClickListener;

    public static class DataHolderObject extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView bTitle;
        TextView bDate;

        public DataHolderObject(View itemView) {
            super(itemView);
            bTitle = (TextView) itemView.findViewById(R.id.blogTitle);
            bDate = (TextView) itemView.findViewById(R.id.blogDate);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RVBlogAdapter(List<BlogsList> blogItems) {
        this.blogItems = blogItems;
    }

    @Override
    public DataHolderObject onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_blogs, parent, false);

        DataHolderObject dataObjectHolder = new DataHolderObject(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataHolderObject holder, int position) {
        holder.bTitle.setText(blogItems.get(position).getBlogName());
        holder.bDate.setText(blogItems.get(position).getBlogDate());
    }

    public void addItem(BlogsList dataObj, int index) {
        blogItems.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        blogItems.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return blogItems.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
