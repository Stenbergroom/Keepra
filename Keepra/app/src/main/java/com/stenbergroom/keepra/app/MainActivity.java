package com.stenbergroom.keepra.app;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.stenbergroom.keepra.app.adapter.ApplicationAdapter;
import com.stenbergroom.keepra.app.entity.AppInfo;
import com.stenbergroom.keepra.app.itemanimator.CustomItemAnimator;
import com.stenbergroom.keepra.app.util.UploadHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private List<AppInfo> applicationList = new ArrayList<AppInfo>();

    private ApplicationAdapter mAdapter;
    private FloatingActionsMenu mFabButton;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private static UploadHelper.UploadComponentInfoTask uploadComponentInfoTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle DrawerLayout
        DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        // Handle ActionBarDrawerToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();

        // Handle different Drawer States :D
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        // Handle DrawerList
        LinearLayout mDrawerList = (LinearLayout)findViewById(R.id.drawer_left);

        // Handle ProgressBar
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        ((ImageView)mDrawerList.findViewById(R.id.drawer_opensource_icon)).setImageDrawable(new IconicsDrawable(this, FontAwesome.Icon.faw_github).colorRes(R.color.secondary).actionBarSize());

        // Fab Button
        mFabButton = (FloatingActionsMenu)findViewById(R.id.fab_button);

        mRecyclerView = (RecyclerView)findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new CustomItemAnimator());

        mAdapter = new ApplicationAdapter(new ArrayList<AppInfo>(), R.layout.row_application, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent));
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new InitializeApplicationTask().execute();
            }
        });

        new InitializeApplicationTask().execute();

        if(savedInstanceState != null){
            if(uploadComponentInfoTask != null){
                if(uploadComponentInfoTask.isRunning){
                    uploadComponentInfoTask.showProgress(this);
                }
            }
        }

        // Show Progress
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void animateActivity(AppInfo appInfo, View appIcon){
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("appInfo", appInfo.getComponentName());

        // animation fab, icon
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create((View) mFabButton, "fab"));
        startActivity(i, transitionActivityOptions.toBundle());
    }

    private class InitializeApplicationTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mAdapter.clearApplications();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            applicationList.clear();

            //Query the applications
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
            for(ResolveInfo ri : ril){
                applicationList.add(new AppInfo(MainActivity.this, ri));
            }

            Collections.sort(applicationList);

            for(AppInfo appInfo : applicationList){
                // Load icons before shown. so the list is smoother
                appInfo.getIcon();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Handle visibility
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);

            // Set data for list
            mAdapter.addApplications(applicationList);
            mSwipeRefreshLayout.setRefreshing(false);

            super.onPostExecute(aVoid);
        }
    }
}