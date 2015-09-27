package com.roubow.xufnotify.data;

import com.roubow.xufnotify.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
        if (mEventBeanList.isEmpty()){
            _addEventAndDateItem(0, e);
            return;
        }
        if (DateUtil.compareDay(e.getDoEventDate(), mEventBeanList.get(0).getDoEventDate()) > 0){
            _addEventAndDateItem(0, e);
            return;
        }
        if (DateUtil.compareDay(e.getDoEventDate(), mEventBeanList.get(0).getDoEventDate()) == 0){
            for (int i = 1; i < mEventBeanList.size(); i++){
                if (e.getDoEventDate().after(mEventBeanList.get(i).getDoEventDate())){
                    mEventBeanList.add(i, e);
                    return;
                }
            }
        }
        if (DateUtil.compareDay(e.getDoEventDate(), mEventBeanList.get(0).getDoEventDate()) < 0){
            for (int i = 1; i < mEventBeanList.size(); i++){
                if (DateUtil.compareDay(e.getDoEventDate(), mEventBeanList.get(i).getDoEventDate()) == 0){
                    for (int j = i + 1; j < mEventBeanList.size(); j++){
                        if (e.getDoEventDate().after(mEventBeanList.get(j).getDoEventDate())){
                            mEventBeanList.add(j, e);
                            return;
                        }
                    }
                }
                if (DateUtil.compareDay(e.getDoEventDate(), mEventBeanList.get(i).getDoEventDate()) > 0){
                    _addEventAndDateItem(i, e);
                    return;
                }
            }
            _addEventAndDateItem(mEventBeanList.size() - 1, e);
        }
    }

    private void _addEventAndDateItem(int pos, EventBean eventItem) {
        EventBean dateItemEvent = new EventBean();
        dateItemEvent.setDateItem(true);
        dateItemEvent.setDoEventDate(eventItem.getDoEventDate());
        mEventBeanList.add(pos, eventItem);
        mEventBeanList.add(pos, dateItemEvent);
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
