package com.socialinfotech.realm.list;

import io.realm.RealmObject;

/**
 * Created by darshit on 12/8/15.
 */
public class ListData extends RealmObject {
    private int id;
    private String name;
    private int progress;
    private String thumnail;
    private String file_name;
    private int status;

    //-2=queue
    //    -1=error;
//    0=downloading
//1=comple
//    2=retry
    public String getName() {
        return name;
    }

    public int getProgress() {
        return progress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setName(String name) {
        this.name = name;
    }
}
