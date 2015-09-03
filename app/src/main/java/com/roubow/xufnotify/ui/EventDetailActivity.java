package com.roubow.xufnotify.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.roubow.xufnotify.R;
import com.roubow.xufnotify.data.EventBean;
import com.roubow.xufnotify.data.EventLab;
import com.roubow.xufnotify.util.DateUtil;
import com.roubow.xufnotify.widget.DateTimePicker;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Xuf on 2015/8/29.
 */
public class EventDetailActivity extends SherlockActivity implements DateTimePicker.DateTimeChangeCallback{

    private EditText mDetailEditText;
    private TextView mSetTimeTextView;
    private DateTimePicker mDateTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        setTitle("备忘详情");

        _init();
    }

    private void _init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        mDetailEditText = (EditText)findViewById(R.id.et_event_detail);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //等待UI加载完成再弹出软键盘
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mDetailEditText, 0);
            }
        }, 500);

        mDateTimePicker = (DateTimePicker)findViewById(R.id.date_time_picker);
        mDateTimePicker.setCallback(this);

        mSetTimeTextView = (TextView)findViewById(R.id.tv_set_notify_time);
        mSetTimeTextView.setText("设置提醒时间：" + DateUtil.getCurrentDateString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.opt_menu_event_detail_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_finish_add_event:
                if (_saveEvent()){
                    finish();
                }

                break;
        }

        return true;
    }

    @Override
    public void onDateTimeChange(Date date) {
        mSetTimeTextView.setText("设置提醒时间：" + DateUtil.getDateString(date));
    }

    private boolean _saveEvent(){
        String eventContent = mDetailEditText.getText().toString();

        if (eventContent.equals("")){
            Toast.makeText(this, "请输入待办备忘", Toast.LENGTH_SHORT).show();
            return false;
        }

        Date notifyDate = mDateTimePicker.getDate();
        Date curDate = new Date(System.currentTimeMillis());

        if (notifyDate.before(curDate)){
            Toast.makeText(this, "请输入未来的时间点", Toast.LENGTH_SHORT).show();
            return false;
        }

        EventBean eventBean = new EventBean();
        eventBean.setNotifyDate(notifyDate);
        eventBean.setEventContent(eventContent);

        EventLab eventLab = EventLab.getInstance();
        eventLab.addEventEx(eventBean);

        return true;
    }
}
