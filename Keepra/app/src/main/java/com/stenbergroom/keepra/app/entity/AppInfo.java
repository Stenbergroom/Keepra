package com.stenbergroom.keepra.app.entity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Sten on 08.05.2015.
 */
public class AppInfo implements Comparable<Object> {

    private Context ctx;
    private ResolveInfo ri;
    private ComponentName componentName = null;
    private PackageInfo pi = null;
    private Drawable icon = null;

    String name = null;

    public AppInfo(Context ctx, ResolveInfo ri){
        this.ctx = ctx;
        this.ri = ri;

        this.componentName = new ComponentName(ri.activityInfo.applicationInfo.packageName, ri.activityInfo.name);

        try {
            pi = ctx.getPackageManager().getPackageInfo(getPackageName(), 0);
        }catch (PackageManager.NameNotFoundException e){
        }
    }

    public String getName(){
        if(name != null){
            return name;
        }else{
            try {
                return getNameFromResolveInfo(ri);
            }catch (PackageManager.NameNotFoundException e){
                return getPackageName();
            }
        }
    }

    public String getActivityName(){
        return ri.activityInfo.name;
    }

    public String getPackageName(){
        return ri.activityInfo.packageName;
    }

    public ComponentName getComponentName(){
        return componentName;
    }

    public String getComponentInfo(){
        if(getComponentName() != null){
            return getComponentName().toString();
        }else{
            return "";
        }
    }

    public ResolveInfo getResolveInfo(){
        return ri;
    }

    public PackageInfo getPackageInfo(){
        return pi;
    }

    public String getVersionName(){
        PackageInfo pi = getPackageInfo();
        if(pi != null){
            return pi.versionName;
        }else{
            return "";
        }
    }

    public int getVersionCode(){
        PackageInfo pi = getPackageInfo();
        if(pi != null){
            return pi.versionCode;
        }else{
            return 0;
        }
    }

    public Drawable getIcon(){
        if(icon == null){
            icon = getResolveInfo().loadIcon(ctx.getPackageManager());
        }
        return icon;
    }
    @SuppressLint("NewApi")
    public long getFirstInstallTime(){
        PackageInfo pi = getPackageInfo();
        if(pi != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return pi.firstInstallTime;
        }else{
            return 0;
        }
    }

    @SuppressLint("NewApi")
    public long getLastUpdateTime(){
        PackageInfo pi = getPackageInfo();
        if(pi != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return pi.lastUpdateTime;
        }else{
            return 0;
        }
    }

    @Override
    public int compareTo(Object o) {
        AppInfo f = (AppInfo) o;
        return getName().compareTo(f.getName());
    }

    @Override
    public String toString() {
        return getName();
    }

    /*
    * Helper method to get an applications name!
    *
    * @param ri
    * @return
    * @throws android.content.pm.PackageManager.NameNotFoundException
    * */

    public String getNameFromResolveInfo(ResolveInfo ri) throws PackageManager.NameNotFoundException{
        String name = ri.resolvePackageName;
        if(ri.activityInfo != null){
            Resources res = ctx.getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
            Resources engRes = getEnglishResources(res);

            if(ri.activityInfo.labelRes != 0){
                name = engRes.getString(ri.activityInfo.labelRes);

                if(name == null || name.equals("")){
                    name = res.getString(ri.activityInfo.labelRes);
                }
            }else{
                name = ri.activityInfo.applicationInfo.loadLabel(ctx.getPackageManager()).toString();
            }
        }
        return name;
    }

    public Resources getEnglishResources(Resources standardResources){
        AssetManager assets = standardResources.getAssets();
        DisplayMetrics metrics = standardResources.getDisplayMetrics();
        Configuration config = new Configuration(standardResources.getConfiguration());
        config.locale = Locale.US;
        return new Resources(assets, metrics, config);
    }

}
