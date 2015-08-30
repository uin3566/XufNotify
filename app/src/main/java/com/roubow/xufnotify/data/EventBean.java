package com.roubow.xufnotify.data;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Xuf on 2015/8/29.
 */
public class EventBean {

    private UUID mId;
    private Date mCreateDate;
    private Date mNotifyDate;
    private String mEventContent;

    public EventBean(){
        mId = UUID.randomUUID();
        mCreateDate = new Date(System.currentTimeMillis());
    }

    public void setEventContent(String content){
        mEventContent = content;
    }

    public String getEventContent(){
        return mEventContent;
    }

    public void setNotifyDate(Date date){
        mNotifyDate = date;
    }

    public Date getNotifyDate(){
        return mNotifyDate;
    }
}
