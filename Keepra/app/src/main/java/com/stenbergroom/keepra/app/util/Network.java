package com.stenbergroom.keepra.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Sten on 08.05.2015.
 */
public class Network {

    private static final String LOG_TAG = "LOG_TAG";

    public static boolean isAvailiable(Context ctx){
        Log.d(LOG_TAG, " - Network.isAvailiable");
        ConnectivityManager comMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = comMgr.getActiveNetworkInfo();
        if(i == null)
            return false;
        if(!i.isConnected())
            return false;
        if(!i.isAvailable())
            return false;
        return true;
    }
}
