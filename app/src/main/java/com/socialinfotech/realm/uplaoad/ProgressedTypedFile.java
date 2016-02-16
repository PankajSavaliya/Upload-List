package com.socialinfotech.realm.uplaoad;

/**
 * Created by pankaj on 05/01/16.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import retrofit.mime.TypedFile;


/**
 * Created by darshit on 10/2/15.
 */

public class ProgressedTypedFile extends TypedFile {

    private static final int BUFFER_SIZE = 4096;
    Listener listener;
    long totalSize = 0;

    public ProgressedTypedFile(String mimeType, File file, Listener listener) {
        super(mimeType, file);
        this.listener = listener;
        totalSize = file.length();
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        FileInputStream in = new FileInputStream(super.file());
        long total = 0;
        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                total += read;
                int percentage = (int) ((total / (float) totalSize) * 100);
                out.write(buffer, 0, read);
                this.listener.onUpdateProgress(percentage);
            }
        } finally {
            in.close();
        }
    }

    public interface Listener {
        void onUpdateProgress(int percentage);
    }
}
