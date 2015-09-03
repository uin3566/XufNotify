package com.roubow.xufnotify.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Xuf on 2015/8/29.
 */
public class EventBean {

    private final String JSON_ID = "id";
    private final String JSON_IS_DATE_ITEM = "is_date_item";
    private final String JSON_CREATE_DATE = "create_date";
    private final String JSON_NOTIFY_DATE = "notify_date";
    private final String JSON_EVENT_CONTENT = "event_content";

    private UUID mId;
    private boolean mIsDateItem;
    private Date mCreateDate;
    private Date mNotifyDate;
    private String mEventContent;

    public EventBean(){
        mId = UUID.randomUUID();
        mCreateDate = new Date(System.currentTimeMillis());
        mIsDateItem = false;
    }

    public EventBean(JSONObject jsonObject) throws JSONException{
        mId = UUID.fromString(jsonObject.getString(JSON_ID));
        mIsDateItem = jsonObject.getBoolean(JSON_IS_DATE_ITEM);
        mCreateDate = new Date(jsonObject.getLong(JSON_CREATE_DATE));
        if (jsonObject.has(JSON_NOTIFY_DATE)){
            mNotifyDate = new Date(jsonObject.getLong(JSON_NOTIFY_DATE));
        }
        if (jsonObject.has(JSON_EVENT_CONTENT)){
            mEventContent = jsonObject.getString(JSON_EVENT_CONTENT);
        }
    }

    public JSONObject toJson() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_ID, mId.toString());
        jsonObject.put(JSON_IS_DATE_ITEM, mIsDateItem);
        jsonObject.put(JSON_CREATE_DATE, mCreateDate.getTime());
        if (mNotifyDate != null){
            jsonObject.put(JSON_NOTIFY_DATE, mNotifyDate.getTime());
        }
        if (mEventContent != null){
            jsonObject.put(JSON_EVENT_CONTENT, mEventContent);
        }
        return jsonObject;
    }

    public void setDateItem(boolean isDateItem){
        mIsDateItem = isDateItem;
    }

    public boolean isDateItem(){
        return mIsDateItem;
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

    public void setCreateDate(Date createDate){
        mCreateDate = createDate;
    }

    public Date getCreateDate(){
        return mCreateDate;
    }
}
