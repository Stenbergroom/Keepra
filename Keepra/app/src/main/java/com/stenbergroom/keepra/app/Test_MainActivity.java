package com.stenbergroom.keepra.app;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.stenbergroom.keepra.app.adapter.Test_CustomAdapter;
import com.stenbergroom.keepra.app.entity.Test_TaskInfo;
import com.stenbergroom.keepra.app.itemanimator.Test_CustomItemAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sten on 13.05.2015.
 */
public class Test_MainActivity extends ActionBarActivity {

    private List<Test_TaskInfo> tasksList = new ArrayList<Test_TaskInfo>();

    private Test_CustomAdapter mAdapter;
    private FloatingActionsMenu fabMenu;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();

        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        fabMenu = (FloatingActionsMenu)findViewById(R.id.fab_menu);

        mRecyclerView = (RecyclerView)findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new Test_CustomItemAnimator());

        mAdapter = new Test_CustomAdapter(new ArrayList<Test_TaskInfo>(), R.layout.test_row_task, Test_MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        
        // TODO
    }
}
