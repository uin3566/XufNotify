package com.roubow.xufnotify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.roubow.xufnotify.R;
import com.roubow.xufnotify.data.EventBean;
import com.roubow.xufnotify.util.DateUtil;

import java.util.List;

/**
 * Created by Xuf on 2015/8/29.
 */
public class TimeTrackAdapter extends BaseAdapter {

    private List<EventBean> mEventBeanList;
    private Context mContext;

    public final int TYPE_EVENT = 0;
    public final int TYPE_DATE = 1;

    public TimeTrackAdapter(Context context){
        mContext = context;
    }

    public void setData(List<EventBean> eventBeanList){
        mEventBeanList = eventBeanList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        EventBean eventBean = (EventBean)getItem(position);
        if (eventBean.isDateItem()){
            type = TYPE_DATE;
        } else {
            type = TYPE_EVENT;
        }

        return type;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        if (mEventBeanList == null || mEventBeanList.isEmpty()){
            return 0;
        }
        return mEventBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEventBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventViewHolder eventHolder = null;
        DateViewHolder dateHolder = null;
        int type = getItemViewType(position);
        EventBean eventBean = mEventBeanList.get(position);
        if (convertView == null){
            if (type == TYPE_DATE){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.time_track_list_date_item, parent, false);
                dateHolder = new DateViewHolder(convertView);
                convertView.setTag(dateHolder);
            } else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.time_track_list_event_item, parent, false);
                eventHolder = new EventViewHolder(convertView);
                convertView.setTag(eventHolder);
            }
        }

        if (type == TYPE_DATE){
            dateHolder = (DateViewHolder)convertView.getTag();
            dateHolder.mCreateDateTextView.setText(DateUtil.getDateStringYMD(eventBean.getCreateDate()));
        } else {
            eventHolder = (EventViewHolder)convertView.getTag();
            eventHolder.mNotifyDateTextView.setText(DateUtil.getDateString(eventBean.getNotifyDate()));
            eventHolder.mEventContentTextView.setText(eventBean.getEventContent());
        }

        return convertView;
    }

    private class EventViewHolder{
        TextView mNotifyDateTextView;
        TextView mEventContentTextView;

        public EventViewHolder(View view){
            mNotifyDateTextView = (TextView)view.findViewById(R.id.tv_notify_date);
            mEventContentTextView = (TextView)view.findViewById(R.id.tv_event_content);
        }
    }

    private class DateViewHolder{
        TextView mCreateDateTextView;

        public DateViewHolder(View view){
            mCreateDateTextView = (TextView)view.findViewById(R.id.tv_create_date);
        }
    }
}
