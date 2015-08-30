package com.roubow.xufnotify.adapter;

import android.annotation.TargetApi;
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

    public TimeTrackAdapter(Context context){
        mContext = context;
    }

    public void setData(List<EventBean> eventBeanList){
        mEventBeanList = eventBeanList;
        notifyDataSetChanged();
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
        ViewHolder holder = null;
        EventBean eventBean = mEventBeanList.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.time_track_list_event_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder = (ViewHolder)convertView.getTag();
        holder.mDateTextView.setText(DateUtil.getDateString(eventBean.getNotifyDate()));
        holder.mEventContentTextView.setText(eventBean.getEventContent());

        return convertView;
    }

    private class ViewHolder{
        TextView mDateTextView;
        TextView mEventContentTextView;

        public ViewHolder(View view){
            mDateTextView = (TextView)view.findViewById(R.id.tv_date);
            mEventContentTextView = (TextView)view.findViewById(R.id.tv_event_content);
        }
    }
}
