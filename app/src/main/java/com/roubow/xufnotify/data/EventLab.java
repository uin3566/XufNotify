package com.roubow.xufnotify.data;

import android.content.Context;

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

    public void deleteEvent(EventBean e){
        if (mEventBeanList.contains(e)){
            mEventBeanList.remove(e);
        }
    }

    public List<EventBean> getEventBeanList(){
        return mEventBeanList;
    }
}
