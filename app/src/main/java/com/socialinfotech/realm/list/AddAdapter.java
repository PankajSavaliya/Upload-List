package com.socialinfotech.realm.list;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.socialinfotech.realm.R;
import com.socialinfotech.realm.searvice.RoundProgressBarWidthNumber;

import java.io.File;
import java.util.List;


/**
 * Created by pankaj on 18/12/15.
 */
public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {

    protected List<ListData> items;
    private int itemLayout;
    private Context mContext;
    public AddAdapter(Context mCon,List<ListData> items, int itemLayout) {
        this.items = items;
        this.mContext=mCon;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ListData item = items.get(position);
        holder.image.setImageURI(Uri.fromFile(new File(item.getThumnail())));
        holder.progress.setProgress(item.getProgress());
        holder.itemView.setTag(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView image;
        public RoundProgressBarWidthNumber progress;
        public CardView itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.profile_pic);
            progress = (RoundProgressBarWidthNumber) itemView.findViewById(R.id.downloadProgressBar);
            itemView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}