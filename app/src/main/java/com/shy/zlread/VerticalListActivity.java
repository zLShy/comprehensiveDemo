package com.shy.zlread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.shy.zlread.adapter.VerticalListAdapter;

import java.util.ArrayList;
import java.util.List;

public class VerticalListActivity extends AppCompatActivity {

    private List<String> mList = new ArrayList<>();
    private VerticalListAdapter mAdapter;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_list_layout);
        mListView = (ListView) findViewById(R.id.list_view);
        for (int i = 0;i<30;i++) {
            mList.add("i ----> "+ i);
        }

        mAdapter = new VerticalListAdapter(this,mList);
        mListView.setAdapter(mAdapter);

    }
}
