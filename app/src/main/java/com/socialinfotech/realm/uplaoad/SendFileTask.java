package com.socialinfotech.realm.uplaoad;

import android.os.AsyncTask;
import android.os.RecoverySystem;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pankaj on 05/01/16.
 */
public class SendFileTask extends AsyncTask<String, Integer, rx.Observable<image>> {
    private RecoverySystem.ProgressListener listener;
    private File filePath;
    private long totalSize;
    final UploadInfo mInfo;

    public SendFileTask(UploadInfo info) {
        this.mInfo = info;
        this.filePath = mInfo.getFilename();
    }

    @Override
    protected rx.Observable<image> doInBackground(String... params) {
        mInfo.setUploadState(UploadInfo.UplaodState.DOWNLOADING);
        ProgressedTypedFile.Listener listener = new ProgressedTypedFile.Listener() {
            @Override
            public void onUpdateProgress(int percentage) {
                publishProgress(percentage);
            }
        };
        ProgressedTypedFile typedFile = new ProgressedTypedFile("image/jpeg", filePath, listener);
        return MyRestAdapter.getInstance().getImageName(typedFile);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.e("progress update", String.format("progress[%d]", values[0]) + "");
        //do something with values[0], its the percentage so you can easily do
        //progressBar.setProgress(values[0]);
        mInfo.setProgress(values[0]);
//        ProgressBar bar = mInfo.getProgressBar();
//        if (bar != null) {
//            bar.setProgress(mInfo.getProgress());
//            bar.invalidate();
//        }
    }

    @Override
    protected void onPostExecute(rx.Observable<image> imageObservable) {
        super.onPostExecute(imageObservable);
        //Log.e("result", imageObservable. + "pankaj");

        imageObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<image>() {
                    @Override
                    public void onCompleted() {
                        Log.e("image name complete", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("image name error", "error");
                        mInfo.setUploadState(UploadInfo.UplaodState.RETRY);
                    }

                    @Override
                    public void onNext(image image) {
                        Log.e("image name", image.getMessage());
                        mInfo.setUploadState(UploadInfo.UplaodState.COMPLETE);
                    }
                });
    }
}
