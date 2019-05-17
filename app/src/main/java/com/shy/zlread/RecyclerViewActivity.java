package com.shy.zlread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shy.zlread.adapter.TestAdapter;
import com.shy.zlread.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private TestAdapter mAdapter;
    private List<TestBean> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        for (int i=0;i<10;i++) {
            mList.add(new TestBean("test->"+i));
        }

        mAdapter = new TestAdapter(mList,this,R.layout.recycler_item);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
