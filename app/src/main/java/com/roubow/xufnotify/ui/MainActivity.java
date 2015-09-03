package com.roubow.xufnotify.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.roubow.xufnotify.R;
import com.roubow.xufnotify.adapter.TimeTrackAdapter;
import com.roubow.xufnotify.data.EventBean;
import com.roubow.xufnotify.data.EventLab;
import com.roubow.xufnotify.util.DimenUtil;
import com.roubow.xufnotify.util.FileUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.List;


public class MainActivity extends SherlockActivity {

    private final String FILE_NAME = "EventList";

    private long mFirstBackPressTime;

    private SwipeMenuListView mListView;
    private TimeTrackAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setData(EventLab.getInstance().getEventBeanList());
    }

    @Override
    protected void onStop() {
        super.onStop();
        try{
            _saveEventsToFile();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void _init(){
        setTitle("备忘");
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        mListView = (SwipeMenuListView)findViewById(R.id.lv_time_track);
        mAdapter = new TimeTrackAdapter(this);
        mListView.setAdapter(mAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                if (swipeMenu.getViewType() == mAdapter.TYPE_EVENT){
                    //star item
                    SwipeMenuItem starItem = new SwipeMenuItem(MainActivity.this);
                    starItem.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1, 0xF5)));
                    starItem.setWidth(DimenUtil.dp2px(MainActivity.this, 70));
                    starItem.setIcon(R.mipmap.ic_star);
                    swipeMenu.addMenuItem(starItem);
                    //delete item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(MainActivity.this);
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                            0x3F, 0x25)));
                    deleteItem.setWidth(DimenUtil.dp2px(MainActivity.this, 70));
                    deleteItem.setIcon(R.mipmap.ic_delete);
                    swipeMenu.addMenuItem(deleteItem);
                }
            }
        };
        mListView.setMenuCreator(creator);
        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu swipeMenu, int index) {
                if (index == 0){
                    EventLab.getInstance().starItemAt(position);
                    mAdapter.notifyDataSetChanged();
                } else {
                    EventLab.getInstance().deleteEventAt(position);
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        try{
            _getEventsFromFile();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void _saveEventsToFile() throws JSONException , IOException{
        List<EventBean> eventBeanList = EventLab.getInstance().getEventBeanList();
        if (eventBeanList == null || eventBeanList.isEmpty()){
            FileUtil.deleteFile(this, FILE_NAME);
            return;
        }

        JSONArray jsonArray = new JSONArray();
        for (EventBean eventBean : eventBeanList){
            JSONObject jsonObject = eventBean.toJson();
            jsonArray.put(jsonObject);
        }

        FileUtil.saveString(this, FILE_NAME, jsonArray.toString());
    }

    private void _getEventsFromFile() throws IOException, JSONException{
        String jsonString = FileUtil.getString(this, FILE_NAME);
        JSONArray jsonArray = (JSONArray)new JSONTokener(jsonString).nextValue();
        EventLab.getInstance().clearLab();
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            EventLab.getInstance().addEvent(new EventBean(jsonObject));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.opt_menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_event:
                Intent i = new Intent(MainActivity.this, EventDetailActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mFirstBackPressTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再按一次推出备忘", Toast.LENGTH_SHORT).show();
        }
        mFirstBackPressTime = System.currentTimeMillis();
    }
}
