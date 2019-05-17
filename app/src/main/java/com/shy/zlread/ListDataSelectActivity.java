package com.shy.zlread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shy.zlread.adapter.ListDataAdapter;
import com.shy.zlread.weight.ListScreenView;

public class ListDataSelectActivity extends AppCompatActivity {

    private ListScreenView mListScreenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data_select);
        mListScreenView = (ListScreenView) findViewById(R.id.lsv);

        mListScreenView.setAdapter(new ListDataAdapter(this));

//
//        findViewById(R.id.content_tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mListScreenView.openMenu();
//                Toast.makeText(ListDataSelectActivity.this,"123",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void click(View view) {
        Toast.makeText(this,"123",Toast.LENGTH_SHORT).show();
    }
}
