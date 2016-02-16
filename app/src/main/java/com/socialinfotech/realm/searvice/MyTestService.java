package com.socialinfotech.realm.searvice;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.RecoverySystem;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.widget.ProgressBar;

import com.socialinfotech.realm.list.ListData;
import com.socialinfotech.realm.uplaoad.MyRestAdapter;
import com.socialinfotech.realm.uplaoad.ProgressedTypedFile;
import com.socialinfotech.realm.uplaoad.UploadInfo;
import com.socialinfotech.realm.uplaoad.image;

import java.io.File;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pankaj on 13/02/16.
 */


public class MyTestService extends IntentService {
    private Handler repeatUpdateHandler = new Handler();
    private static final long REP_DELAY = 2000;
    private int mValue = 0;
    private int Id;
    private ResultReceiver rec;
    private int mInfo_status;

    public MyTestService() {
        super(MyTestService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Extract the receiver passed into the service
        rec = intent.getParcelableExtra("receiver");
        // Extract additional values from the bundle

        Id = intent.getIntExtra("id", 0);
        UplaodImage(intent.getStringExtra("path"));
        //new RptUpdater().run();
    }

    private void UplaodImage(String path) {
        mInfo_status = 0;
        ProgressedTypedFile.Listener listener = new ProgressedTypedFile.Listener() {
            @Override
            public void onUpdateProgress(int percentage) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", Id);
                bundle.putInt("progress", percentage);
                // Here we call send passing a resultCode and the bundle of extras
                rec.send(Activity.RESULT_OK, bundle);
            }
        };
        ProgressedTypedFile typedFile = new ProgressedTypedFile("image/jpeg", new File(path), listener);
        rx.Observable<image> imageObservable = MyRestAdapter.getInstance().getImageName(typedFile);
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
                        mInfo_status = 2;
                    }

                    @Override
                    public void onNext(image image) {
                        Log.e("image name", image.getMessage());
                        mInfo_status = 1;
                    }
                });
    }

    private class RptUpdater implements Runnable {
        public void run() {
            decrement();
            repeatUpdateHandler.postDelayed(new RptUpdater(), REP_DELAY);
        }

    }

    public void decrement() {
        mValue++;
        // To send a message to the Activity, create a pass a Bundle
        Bundle bundle = new Bundle();
        bundle.putInt("id", Id);
        bundle.putInt("progress", mValue);
        // Here we call send passing a resultCode and the bundle of extras
        rec.send(Activity.RESULT_OK, bundle);
    }


}
