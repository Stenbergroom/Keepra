package com.stenbergroom.keepra.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Sten on 27.05.2015.
 */
public class Test_NewTaskActivity extends ActionBarActivity{

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_new_task);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar title");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
