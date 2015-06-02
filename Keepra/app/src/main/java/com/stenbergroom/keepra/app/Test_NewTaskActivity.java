package com.stenbergroom.keepra.app;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dd.CircularProgressButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

/**
 * Created by Sten on 27.05.2015.
 */
public class Test_NewTaskActivity extends ActionBarActivity{

    private static final int SCALE_DELAY = 30;

    private Toolbar toolbar;
    private LinearLayout rowContainer;
    private FloatingActionsMenu fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_new_task);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar title");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabMenu = (FloatingActionsMenu)findViewById(R.id.fab_menu);
        rowContainer = (LinearLayout)findViewById(R.id.row_container);
        final CircularProgressButton circularButton1 = (CircularProgressButton)findViewById(R.id.circularButton1);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    simulateSuccessProgress(circularButton1);
                } else {
                    circularButton1.setProgress(0);
                }
            }
        });

        final CircularProgressButton circularButton2 = (CircularProgressButton)findViewById(R.id.circularButton2);
        circularButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(circularButton2.getProgress() == 0){
                    simulateErrorProgress(circularButton2);
                }else{
                    circularButton2.setProgress(0);
                }
            }
        });

        for(int i = 0; i < rowContainer.getChildCount(); i++){
            View rowView = rowContainer.getChildAt(i);
            rowView.animate().setStartDelay(100 + i * SCALE_DELAY).scaleX(1).scaleY(1);
        }

        View view = rowContainer.findViewById(R.id.row_task_time);
        fillRow(view, "Task Time", "Time");

        view = rowContainer.findViewById(R.id.row_task_date);
        fillRow(view, "Task Date", "Date");

        view = rowContainer.findViewById(R.id.row_task_title);
        fillRow(view, "Task Title", "Title");

        view = rowContainer.findViewById(R.id.row_task_description);
        fillRow(view, "Task Description", "Description");

        //view = rowContainer.findViewById(R.id.row_task_status);
        //fillRow(view, "Task Status", "Status");


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                Test_NewTaskActivity.this.onBackPressed();
            }
        });
    }

    public void fillRow(View view, final String title, final String hintText){
        TextView titleView = (TextView)view.findViewById(R.id.title);
        titleView.setText(title);

        EditText descriptionView = (EditText)view.findViewById(R.id.description);
        descriptionView.setHint(hintText);
    }

    @Override
    public void onBackPressed() {
        for(int i = rowContainer.getChildCount() - 1; i >= 0; i--){
            final View rowView = rowContainer.getChildAt(i);
            ViewPropertyAnimator propertyAnimator = rowView.animate().setStartDelay((rowContainer.getChildCount() - 1 - i) * SCALE_DELAY).scaleX(0).scaleY(0);

            propertyAnimator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        finishAfterTransition();
                    }else{
                        finish();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        fabMenu.collapse();
    }

    private void simulateSuccessProgress(final CircularProgressButton button){
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    private void simulateErrorProgress(final CircularProgressButton button){
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if(value == 99){
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }

}
