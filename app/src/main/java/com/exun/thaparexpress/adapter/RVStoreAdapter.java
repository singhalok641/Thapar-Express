package com.exun.thaparexpress.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exun.thaparexpress.R;
import com.exun.thaparexpress.activity.fragments.Store;
import com.exun.thaparexpress.model.BlogsList;
import com.exun.thaparexpress.model.StoreList;

import java.util.List;

/**
 * Created by root on 6/2/16.
 */
public class RVStoreAdapter extends RecyclerView.Adapter<RVStoreAdapter.DataHolderObject> {

    private static String LOG_TAG = "RVBlogAdapter";
    private List<StoreList> storeLists;
    private static MyClickListener myClickListener;

    public static class DataHolderObject extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView sName,sCondition,sCost;
        ImageView sImage;
        LinearLayout ll;

        public DataHolderObject(View itemView) {
            super(itemView);
            sName = (TextView) itemView.findViewById(R.id.item);
            sCondition = (TextView) itemView.findViewById(R.id.condition);
            sCost = (TextView) itemView.findViewById(R.id.cost);
            sImage = (ImageView) itemView.findViewById(R.id.itemImage);
            ll = (LinearLayout) itemView.findViewById(R.id.itemImageLayout);
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

    public RVStoreAdapter(List<StoreList> storeLists) {
        this.storeLists = storeLists;
    }

    @Override
    public DataHolderObject onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_store, parent, false);

        DataHolderObject dataObjectHolder = new DataHolderObject(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataHolderObject holder, int position) {
        String cat = storeLists.get(position).getCat();
        if (cat.equals("Book")){
            holder.sImage.setImageResource(R.drawable.ic_book_open_variant_white_48dp);
        } else if (cat.equals("Drafter")){
            holder.sImage.setImageResource(R.drawable.ic_android_studio_white_48dp);
        } else if (cat.equals("Utility")){
            holder.sImage.setImageResource(R.drawable.ic_screwdriver_white_48dp);
        } else if (cat.equals("Clothes")){
            holder.sImage.setImageResource(R.drawable.ic_drawing_white_48dp);
        } else if (cat.equals("Electronics")){
            holder.sImage.setImageResource(R.drawable.ic_cellphone_basic_white_48dp);
        } else if (cat.equals("Cycle")){
            holder.sImage.setImageResource(R.drawable.ic_bike_white_48dp);
        }
        holder.sName.setText(storeLists.get(position).getName());
        holder.sCondition.setText(storeLists.get(position).getCondition());
        String price = "Rs. " + storeLists.get(position).getPrice();
        holder.sCost.setText(price);
    }

    public void addItem(StoreList dataObj, int index) {
        storeLists.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        storeLists.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return storeLists.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }


}
