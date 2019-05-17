package com.shy.zlread;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_list_layout);
//        findViewById(R.id.content_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("TAG","click");
//                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
