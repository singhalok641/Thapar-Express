package com.exun.thaparexpress.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exun.thaparexpress.R;
import com.exun.thaparexpress.model.TimeTableModel;

/**
 * Created by root on 8/13/16.
 */
public class TimeTableAdapter extends CursorRecyclerViewAdapter<TimeTableAdapter.ViewHolder> {

    private static final String TAG = "TimeTableAdapter";
    private static MyClickListener myClickListener;
    private Context context;

    public TimeTableAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_lecture, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        final TimeTableModel lectureList = new TimeTableModel(cursor);

        viewHolder.mTextView.setText(lectureList.getLecture());
        viewHolder.mTextView_teacher.setText(lectureList.getTeacher());
        viewHolder.mTextView_venue.setText(lectureList.getVenue());
        viewHolder.mTextView_start_time.setText(lectureList.getStart_time());
        viewHolder.mTextView_end_time.setText("- "+lectureList.getEnd_time()+": ");



        viewHolder.mCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myClickListener.onItemClick(lectureList.getId(),v);
            }
        });

    }


    public void setOnItemClickListener(MyClickListener myClickListener) {
        TimeTableAdapter.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        void onItemClick(int id, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public CardView mCV;
        public TextView mTextView_teacher;
        public TextView mTextView_venue;
        public TextView mTextView_start_time;
        public TextView mTextView_end_time;


        public ViewHolder(View view) {
            super(view);
            mTextView_start_time = (TextView)view.findViewById(R.id.start_time);
            mTextView_end_time=(TextView)view.findViewById(R.id.end_time);
            mTextView = (TextView)view.findViewById(R.id.lecture);
            mCV = (CardView)view.findViewById(R.id.cv);
            mTextView_teacher = (TextView)view.findViewById(R.id.teacher);
            mTextView_venue=(TextView)view.findViewById(R.id.venue);


        }
    }
}