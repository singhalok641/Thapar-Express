package com.exun.thaparexpress.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.model.EventsList;

import java.util.List;

/**
 * Created by root on 3/2/16.
 */
public class RVEventOAdapter extends RecyclerView.Adapter<RVEventOAdapter.DataHolderObject> {

    private static String LOG_TAG = "RVEventOAdapter";
    private List<EventsList> eventsItems;
    private static MyClickListener myClickListener;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public static class DataHolderObject extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView eTitle,eDate,eDesc;
        NetworkImageView eventImage;

        public DataHolderObject(View itemView) {
            super(itemView);
            eTitle = (TextView) itemView.findViewById(R.id.eventTitle);
            eDate = (TextView) itemView.findViewById(R.id.eventDate);
            eDesc = (TextView) itemView.findViewById(R.id.eventShortDesc);
            eventImage = (NetworkImageView) itemView.findViewById(R.id.eventImage);
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

    public RVEventOAdapter(List<EventsList> eventsItems) {
        this.eventsItems = eventsItems;
    }

    @Override
    public DataHolderObject onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_event, parent, false);

        DataHolderObject dataObjectHolder = new DataHolderObject(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataHolderObject holder, int position) {
        holder.eTitle.setText(eventsItems.get(position).getEventName());
        holder.eDate.setText(eventsItems.get(position).getDate());
        holder.eDesc.setText(eventsItems.get(position).getEventShortDesc());
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        holder.eventImage.setImageUrl(eventsItems.get(position).getEventImage(), imageLoader);
    }

    public void addItem(EventsList dataObj, int index) {
        eventsItems.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        eventsItems.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return eventsItems.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
