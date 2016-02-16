package com.socialinfotech.realm.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.socialinfotech.realm.R;
import com.socialinfotech.realm.searvice.MyTestReceiver;
import com.socialinfotech.realm.searvice.MyTestService;
import com.socialinfotech.realm.searvice.RoundProgressBarWidthNumber;

import io.realm.Realm;
import io.realm.RealmResults;

public class ListActivity extends AppCompatActivity implements ImageChooserListener {

    private Realm realm;
    public MyTestReceiver receiverForTest;
    private ListAdapter adapter;
    private ListView listView;

    //image
    private final static String TAG = "ICA";
    private ImageChooserManager imageChooserManager;
    private String filePath;
    private int chooserType;
    private boolean isActivityResultOver = false;
    private String originalFilePath;
    private String thumbnailFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_my);


        realm = Realm.getInstance(ListActivity.this);
        listView = (ListView) findViewById(R.id.listView);
        RealmResults<ListData> timeStamps = realm.where(ListData.class).findAll();
        adapter = new ListAdapter(ListActivity.this, R.layout.raw, timeStamps, true);

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                ListData timeStamp = adapter.getRealmResults().get(i);
                onStartService(i, timeStamp.getFile_name());
                return true;
            }
        });
        setupServiceReceiver();

    }

    // Starts the IntentService
    public void onStartService(int postion, String file_name) {
        Intent i = new Intent(this, MyTestService.class);
        i.putExtra("id", postion);
        i.putExtra("path", file_name);
        i.putExtra("receiver", receiverForTest);
        startService(i);
    }

    // Setup the callback for when data is received from the service
    public void setupServiceReceiver() {
        receiverForTest = new MyTestReceiver(new Handler());
        // This is where we specify what happens when data is received from the service
        receiverForTest.setReceiver(new MyTestReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == RESULT_OK) {
                    int id = resultData.getInt("id");
                    int progress = resultData.getInt("progress");
                    //  Toast.makeText(ListActivity.this, resultValue, Toast.LENGTH_SHORT).show();
                    ListData timeStamp = adapter.getRealmResults().get(id);
                    realm.beginTransaction();
                    timeStamp.setProgress(progress);
                    realm.commitTransaction();
                  //  updateListView(id, progress);
                }
            }
        });
    }


    public void updateListView(final int position, final int newProgress) {


        final int first = listView.getFirstVisiblePosition();
        int last = listView.getLastVisiblePosition();
        if (position < first || position > last) {
            //just update your DataSet
            //the next time getView is called
            //the ui is updated automatically
            Log.e("if", "if");
            return;
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Log.e("else", "else" + newProgress);
                View convertView = listView.getChildAt(position - first);
                //this is the convertView that you previously returned in getView
                //just fix it (for example:)
                RoundProgressBarWidthNumber bar = (RoundProgressBarWidthNumber) convertView.findViewById(R.id.downloadProgressBar);
                bar.setProgress(newProgress);
            }
        }, 100);


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // realm.close(); // Remember to close Realm when done.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {

            Photo();
        }
        return true;
    }

    protected void Photo() {
        final AlertDialog builderSingle = new AlertDialog.Builder(ListActivity.this).create();
        View view = LayoutInflater.from(ListActivity.this).inflate(R.layout.picture, null, false);
        builderSingle.setView(view, 0, 0, 0, 0);
        Button camera_button = (Button) view.findViewById(R.id.camera_button);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderSingle.dismiss();
                openCemera();
                // EasyImage.openCamera(mContext);
            }
        });
        Button gallery_button = (Button) view.findViewById(R.id.gallery_button);
        gallery_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderSingle.dismiss();
                openGalleryPicker();
                // EasyImage.openGalleryPicker(mContext);
            }
        });

        builderSingle.show();

    }

    public void openCemera() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(ListActivity.this);
        try {
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openGalleryPicker() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        imageChooserManager.setImageChooserListener(ListActivity.this);
        imageChooserManager.clearOldFiles();
        try {
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (image != null) {
                    isActivityResultOver = true;
                    originalFilePath = image.getFilePathOriginal();
                    thumbnailFilePath = image.getFileThumbnail();


                    loadImage(thumbnailFilePath, image.getFilePathOriginal());
                }
            }
        });
    }


    @Override
    public void onError(final String reason) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
            }
        });
    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("activity_result_over", isActivityResultOver);
        outState.putInt("chooser_type", chooserType);
        outState.putString("media_path", filePath);
        outState.putString("orig", originalFilePath);
        outState.putString("thumb", thumbnailFilePath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("chooser_type")) {
                chooserType = savedInstanceState.getInt("chooser_type");
            }
            if (savedInstanceState.containsKey("media_path")) {
                filePath = savedInstanceState.getString("media_path");
            }
            if (savedInstanceState.containsKey("activity_result_over")) {
                isActivityResultOver = savedInstanceState.getBoolean("activity_result_over");
                originalFilePath = savedInstanceState.getString("orig");
                thumbnailFilePath = savedInstanceState.getString("thumb");
            }
        }
        if (isActivityResultOver) {
            populateData();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void populateData() {
        loadImage(thumbnailFilePath, originalFilePath);
    }

    private void loadImage(final String thumb, final String original) {
        //Uri.fromFile(new File(path));
        if (chooserType == ChooserType.REQUEST_PICK_PICTURE || chooserType == ChooserType.REQUEST_CAPTURE_PICTURE) {
            //mAdapter.add(new UploadInfo(new File(thumb), new File(original));
            Log.e("path", original + "path");
            realm.beginTransaction();
            ListData data = realm.createObject(ListData.class);
            data.setName(Long.toString(System.currentTimeMillis()));
            data.setStatus(-2);
            data.setProgress(0);
            data.setFile_name(original);
            data.setThumnail(thumb);
            realm.commitTransaction();
            adapter.notifyDataSetChanged();
        }
    }


}
