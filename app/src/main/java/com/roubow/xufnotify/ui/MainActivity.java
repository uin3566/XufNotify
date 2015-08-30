package com.roubow.xufnotify.ui;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.roubow.xufnotify.R;
import com.roubow.xufnotify.adapter.TimeTrackAdapter;
import com.roubow.xufnotify.data.EventLab;


public class MainActivity extends SherlockActivity {

    private long mFirstBackPressTime;

    private ListView mListView;
    private TimeTrackAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("备忘");

        _init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setData(EventLab.getInstance().getEventBeanList());
    }

    private void _init(){
        mListView = (ListView)findViewById(R.id.lv_time_track);
        mAdapter = new TimeTrackAdapter(this);
        mListView.setAdapter(mAdapter);
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
