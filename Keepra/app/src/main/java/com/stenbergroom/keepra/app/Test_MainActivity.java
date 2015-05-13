package com.stenbergroom.keepra.app;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.stenbergroom.keepra.app.adapter.Test_CustomAdapter;
import com.stenbergroom.keepra.app.entity.Test_TaskInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sten on 13.05.2015.
 */
public class Test_MainActivity extends ActionBarActivity {

    private List<Test_TaskInfo> tasksList = new ArrayList<Test_TaskInfo>();

    private Test_CustomAdapter adapter;
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
    }
}
