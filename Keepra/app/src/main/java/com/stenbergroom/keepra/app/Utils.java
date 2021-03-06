package com.stenbergroom.keepra.app;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Sten on 08.05.2015.
 */
public class Utils {

    public static void configureFab(View fabButton){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            fabButton.setOutlineProvider(new ViewOutlineProvider() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(View view, Outline outline) {
                    int fabSize = view.getContext().getResources().getDimensionPixelSize(R.dimen.fab_size);
                    outline.setOval(0, 0, fabSize, fabSize);
                }
            });
        }else{
            ((ImageButton)fabButton).setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

}