package com.roubow.xufnotify.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.roubow.xufnotify.R;


public class MainActivity extends SherlockActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _init();
    }

    private void _init(){
        mListView = (ListView)findViewById(R.id.lv_time_track);
    }
}
