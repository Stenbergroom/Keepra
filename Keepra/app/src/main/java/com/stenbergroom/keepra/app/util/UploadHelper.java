package com.stenbergroom.keepra.app.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import com.nispok.snackbar.Snackbar;
import com.stenbergroom.keepra.app.R;
import com.stenbergroom.keepra.app.entity.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sten on 08.05.2015.
 */
public class UploadHelper {

    private static final String LOG_TAG = "LOG_TAG";

    private static UploadHelper instance = null;
    private ActionBarActivity act;
    private List<AppInfo> applicationList = new ArrayList<AppInfo>();

    private UploadHelper(ActionBarActivity act, List<AppInfo> applicationList){
        this.act = act;

        if(applicationList != null){
            this.applicationList = applicationList;
        }else{
            this.applicationList = new ArrayList<AppInfo>();
        }
    }

    public static UploadHelper getInstance(ActionBarActivity act, List<AppInfo> applicationList){
        Log.d(LOG_TAG, " - UploadHelper.getInstance");
        if(instance == null){
            instance = new UploadHelper(act, applicationList);
        }else if(act != null){
            instance.act = act;
        }
        return instance;
    }

    public UploadComponentInfoTask upload(AppInfo appInfo){
        UploadComponentInfoTask ucit = new UploadComponentInfoTask();
        ucit.execute(appInfo);
        return ucit;
    }

    public UploadComponentInfoTask uploadAll(){
        UploadComponentInfoTask ucit = new UploadComponentInfoTask();
        ucit.execute();
        return ucit;
    }

    public class UploadComponentInfoTask extends AsyncTask<AppInfo, Integer, Boolean> {
        ProgressDialog mProgressDialog;

        public boolean isRunning = false;

        public void showProgress(Activity act){
            Log.d(LOG_TAG, " - UploadHelper.UploadComponentInfoTask.showProgress");
            mProgressDialog = new ProgressDialog(act);
            mProgressDialog.setTitle(R.string.dialog_uploading);
            mProgressDialog.setMessage(act.getString(R.string.dialog_processingandupploading));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMax(applicationList.size());
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            mProgressDialog.show();
        }

        @Override
        protected void onPreExecute() {
            Log.d(LOG_TAG, " - UploadHelper.UploadComponentInfoTask.onPreExecute");
            if(!Network.isAvailiable(act)){
                this.cancel(true);
                Snackbar.with(act).text(act.getString(R.string.dialog_nointernet)).show(act);
            }else{
                showProgress(act);
            }

            isRunning = true;

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(AppInfo... params) {
            Log.d(LOG_TAG, " - UploadHelper.UploadComponentInfoTask.doInBackground");
            boolean updateRequired = false;
            if(params == null || params.length == 0){
                mProgressDialog.setMax(applicationList.size());

                int i = 0;
                for(AppInfo ai : applicationList){
                    updateRequired = postData();
                    publishProgress(i);
                    if(updateRequired){
                        break;
                    }
                    i++;
                }
            }else if(params.length == 1){
                updateRequired = postData();
                publishProgress(applicationList.size());
            }

            return updateRequired;
        }

        @Override
        protected void onPostExecute(Boolean updateRequired) {
            Log.d(LOG_TAG, " - UploadHelper.UploadComponentInfoTask.onPostExecute");
            isRunning = false;

            if(mProgressDialog != null){
                mProgressDialog.dismiss();
            }
            super.onPostExecute(updateRequired);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(LOG_TAG, " - UploadHelper.UploadComponentInfoTask.onProgressUpdate");
            if(values.length > 0 && mProgressDialog != null){
                mProgressDialog.setProgress(values[0]);
            }
            super.onProgressUpdate(values);
        }

        public boolean postData(){
            Log.d(LOG_TAG, " - UploadHelper.UploadComponentInfoTask.postData");
            try{
                Thread.sleep(100);
            }catch (Exception ex){
            }

            return false;
        }
    }
}