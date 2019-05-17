package com.shy.zlread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

public class BehavorActivity extends AppCompatActivity {

    private Toolbar mtoolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavor);

        this.mtoolBar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mtoolBar);

    }
}
