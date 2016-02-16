package com.socialinfotech.realm.uplaoad;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by pankaj on 05/01/16.
 */
public class MyRestAdapter {

    private api myService;
    private static MyRestAdapter myRestAdapter;

    public MyRestAdapter() {
        myService = restAdapter().create(api.class);
    }

    public static synchronized MyRestAdapter getInstance() {
        if (myRestAdapter == null) {
            myRestAdapter = new MyRestAdapter();
        }
        return myRestAdapter;
    }

    public RestAdapter restAdapter() {

        RestAdapter restAdapter;


        restAdapter = new RestAdapter.Builder().setEndpoint(api.API)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();


        return restAdapter;
    }

    //file upload upload
    public Observable<image> getImageName(ProgressedTypedFile typedFile) {
        return myService.getImageName(typedFile);
    }


}
