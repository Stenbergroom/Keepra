package com.stenbergroom.keepra.app;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.astuetz.PagerSlidingTabStrip;
import com.stenbergroom.keepra.app.adapter.MyPagerAdapter;

public class MainActivity extends ActionBarActivity {

/*    @InjectView(R.id.toolbar)
    Toolbar toolbar;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        ButterKnife.inject(this);*/

/*        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Keepra");
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

/*        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));*/

/*        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabs.setViewPager(pager);*/
        // TODO fix bug in theme
    }
}
