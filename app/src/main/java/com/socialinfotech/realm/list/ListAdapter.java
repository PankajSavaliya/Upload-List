/*
 * Copyright 2014 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.socialinfotech.realm.list;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.socialinfotech.realm.R;
import com.socialinfotech.realm.searvice.RoundProgressBarWidthNumber;

import java.io.File;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class ListAdapter extends RealmBaseAdapter<ListData> implements android.widget.ListAdapter {

    private static class ViewHolder {
        SimpleDraweeView profile_pic;
        RoundProgressBarWidthNumber downloadProgressBar;
    }

    public ListAdapter(Context context, int resId, RealmResults<ListData> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.raw, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.profile_pic = (SimpleDraweeView) convertView.findViewById(R.id.profile_pic);
            viewHolder.downloadProgressBar = (RoundProgressBarWidthNumber) convertView.findViewById(R.id.downloadProgressBar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ListData item = realmResults.get(position);
        viewHolder.profile_pic.setImageURI(Uri.fromFile(new File(item.getThumnail())));
//        viewHolder.profile_pic.setImageURI(Uri.parse(item.getThumnail()));

        viewHolder.downloadProgressBar.setProgress(item.getProgress());

        return convertView;
    }

    public RealmResults<ListData> getRealmResults() {
        return realmResults;
    }
}
