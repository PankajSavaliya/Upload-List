package com.socialinfotech.realm.uplaoad;

/**
 * Created by pankaj on 05/01/16.
 */

import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;


public class UploadInfo {
    private final static String TAG = UploadInfo.class.getSimpleName();


    public enum UplaodState {
        NOT_STARTED,
        QUEUED,
        DOWNLOADING,
        COMPLETE, CANCEL, RETRY
    }

    private volatile UplaodState mDownloadState = UplaodState.NOT_STARTED;
    private File mFilename;
    private volatile Integer mProgress;
    private int id;

    public UploadInfo(File filename) {
        mFilename = filename;
        mProgress = 0;
    }

    public void setUploadState(UplaodState state) {
        mDownloadState = state;
    }

    public UplaodState getUploadState() {
        return mDownloadState;
    }

    public Integer getProgress() {
        return mProgress;
    }

    public void setProgress(Integer progress) {
        this.mProgress = progress;
    }


    public File getFilename() {
        return mFilename;
    }

    public int getId() {
        return id;
    }
}