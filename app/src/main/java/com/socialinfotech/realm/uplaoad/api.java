package com.socialinfotech.realm.uplaoad;


import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import rx.Observable;

public interface api {


    String API = "your api";


    @Multipart
    @POST("/upload")
    Observable<image> getImageName(@Part("mimage") ProgressedTypedFile attachments);

}