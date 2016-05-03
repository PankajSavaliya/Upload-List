# Upload Image in Background
Core Database Reference by Google Developer

##Require Library

    compile 'io.realm:realm-android:0.86.0'
    compile 'com.jakewharton:butterknife:6.0.0'
    compile 'com.squareup.retrofit:retrofit:1.9.0'
    compile 'io.reactivex:rxandroid:1.1.0'
    compile 'com.facebook.fresco:fresco:0.9.0'
    compile 'com.android.support:cardview-v7:23.1.1'
    compile 'com.kbeanie:image-chooser-library:1.5.2@aar'
    compile 'com.android.support:recyclerview-v7:23.1.1'
    
## How to Work?
This Example uplaod MultiPart File(Image) upload to server useing by Retrofit.also uplaod in background if you are exit the application to runnig uploading to the server.
*Uploading Start when you have to long click on listview image.

####Screen Shot
![screen shot 2016-05-03 at 3 29 07 pm](https://cloud.githubusercontent.com/assets/13134958/14980062/e9e4653a-1143-11e6-8ef2-dbc4db836e93.png)
![screen shot 2016-05-03 at 3 28 29 pm](https://cloud.githubusercontent.com/assets/13134958/14980063/e9e80ab4-1143-11e6-8d75-d163e5470f4e.png)
![screen shot 2016-05-03 at 3 33 05 pm](https://cloud.githubusercontent.com/assets/13134958/14980152/7ae3f8d4-1144-11e6-9b3e-3fea3e48bce7.png)

####Implement to your poject
    *In api.java Class in this Example.
    
    @Multipart
    @POST("/upload")
    Observable<image> getImageName(@Part("mimage") ProgressedTypedFile attachments);

##Idea by
http://www.socialinfotech.com/
