package com.roubow.xufnotify.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.roubow.xufnotify.R;
import com.roubow.xufnotify.util.DateUtil;

import net.simonvt.numberpicker.NumberPicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Xuf on 2015/8/30.
 */
public class DateTimePicker extends LinearLayout implements NumberPicker.OnValueChangeListener{

    private final String[] mMonths = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};

    private Context mContext;
    private DateTimeChangeCallback mCallback;

    private NumberPicker mNpYear;
    private NumberPicker mNpMonth;
    private NumberPicker mNpDay;
    private NumberPicker mNpHour;
    private NumberPicker mNpMinute;

    public DateTimePicker(Context context) {
        this(context, null);
    }

    public DateTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        _init();
    }

    private void _init(){
        View root = LayoutInflater.from(mContext).inflate(R.layout.layout_date_picker, this);

        mNpYear = (NumberPicker)root.findViewById(R.id.np_year);
        mNpMonth = (NumberPicker)root.findViewById(R.id.np_month);
        mNpDay = (NumberPicker)root.findViewById(R.id.np_day);
        mNpHour = (NumberPicker)root.findViewById(R.id.np_hour);
        mNpMinute = (NumberPicker)root.findViewById(R.id.np_minute);

        mNpYear.setOnValueChangedListener(this);
        mNpMonth.setOnValueChangedListener(this);
        mNpDay.setOnValueChangedListener(this);
        mNpHour.setOnValueChangedListener(this);
        mNpMinute.setOnValueChangedListener(this);

        Calendar c = Calendar.getInstance();

        //year
        mNpYear.setMinValue(1970);
        mNpYear.setMaxValue(2050);
        mNpYear.setValue(c.get(Calendar.YEAR));

        //month
        mNpMonth.setDisplayedValues(mMonths);
        mNpMonth.setMinValue(0);
        mNpMonth.setMaxValue(mMonths.length - 1);
        mNpMonth.setValue(c.get(Calendar.MONTH));

        //day
        mNpDay.setMaxValue(DateUtil.getDaysOfMonth(c.get(Calendar.YEAR), c.get(Calendar.MONTH)));
        mNpDay.setMinValue(1);
        mNpDay.setValue(c.get(Calendar.DAY_OF_MONTH));

        //hour
        mNpHour.setMaxValue(23);
        mNpHour.setMinValue(0);
        mNpHour.setValue(c.get(Calendar.HOUR_OF_DAY));

        //minute
        mNpMinute.setMaxValue(59);
        mNpMinute.setMinValue(0);
        mNpMinute.setValue(c.get(Calendar.MINUTE));
    }

    public Date getDate(){
        Calendar c = Calendar.getInstance();
        c.set(mNpYear.getValue(), mNpMonth.getValue(), mNpDay.getValue(), mNpHour.getValue(), mNpMinute.getValue());

        return c.getTime();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        int days = DateUtil.getDaysOfMonth(mNpYear.getValue(), mNpMonth.getValue() + 1);

        mNpDay.setMaxValue(days);
        mNpDay.setMinValue(1);

        if (mCallback != null){
            mCallback.onDateTimeChange(getDate());
        }
    }

    public void setCallback(DateTimeChangeCallback callback){
        mCallback = callback;
    }

    public interface DateTimeChangeCallback{
        void onDateTimeChange(Date date);
    }
}
