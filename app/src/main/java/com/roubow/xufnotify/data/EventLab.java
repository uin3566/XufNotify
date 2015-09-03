package com.roubow.xufnotify.data;

import com.roubow.xufnotify.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xuf on 2015/8/29.
 */
public class EventLab {

    private List<EventBean> mEventBeanList;
    private static EventLab mEventLab;

    private EventLab(){
        mEventBeanList = new ArrayList<>();
    }

    public static EventLab getInstance(){
        if (mEventLab == null){
            mEventLab = new EventLab();
        }

        return mEventLab;
    }

    public void addEvent(EventBean e){
        mEventBeanList.add(e);
    }

    public void addEventEx(EventBean e){
        if (mEventBeanList.size() >= 1){
            EventBean previous = mEventBeanList.get(0);
            boolean isDifferentDay = DateUtil.isDifferentDay(previous.getCreateDate(), e.getCreateDate());
            if (isDifferentDay){
                _addEventAndDateItem(e);
            } else {
                mEventBeanList.add(1, e);
            }
        } else {
            _addEventAndDateItem(e);
        }
    }

    //如果添加的项与列表的前一项不是同一天或这是列表项为空，则需要再添加纯日期项
    private void _addEventAndDateItem(EventBean eventItem){
        EventBean dateItemEvent = new EventBean();
        dateItemEvent.setDateItem(true);
        mEventBeanList.add(0, eventItem);
        mEventBeanList.add(0, dateItemEvent);
    }

    public void deleteEventAt(int position){
        int previous = position - 1;
        int next = position + 1;
        EventBean curEvent = mEventBeanList.get(position);
        EventBean preEvent = mEventBeanList.get(previous);
        if (next == mEventBeanList.size()){
            if (preEvent.isDateItem()){
                deleteEvent(curEvent);
                deleteEvent(preEvent);
            } else {
                deleteEvent(curEvent);
            }
        } else {
            EventBean nextEvent = mEventBeanList.get(next);
            if (!nextEvent.isDateItem()){
                deleteEvent(curEvent);
            } else {
                if (preEvent.isDateItem()){
                    deleteEvent(curEvent);
                    deleteEvent(preEvent);
                } else {
                    deleteEvent(curEvent);
                }
            }
        }
    }

    public void deleteEvent(EventBean e){
        if (mEventBeanList.contains(e)){
            mEventBeanList.remove(e);
        }
    }

    public void clearLab(){
        mEventBeanList.clear();
    }

    public List<EventBean> getEventBeanList(){
        return mEventBeanList;
    }
}
