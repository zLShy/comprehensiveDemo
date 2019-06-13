package com.shy.zlread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shy.zlread.weight.ParallaxViewPager;

public class SplashActivity extends AppCompatActivity {

    private ParallaxViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.mViewPager = (ParallaxViewPager) findViewById(R.id.view_pager);

        this.mViewPager.setLayoutIds(getSupportFragmentManager(),new int[] {R.layout.fragment_page_first,R.layout.fragment_page_second,R.layout.fragment_page_third});

    }
}
