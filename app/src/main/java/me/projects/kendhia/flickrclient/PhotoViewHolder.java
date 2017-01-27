package me.projects.kendhia.flickrclient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

class PhotoViewHolder extends RecyclerView.ViewHolder {
    TextView tv_title;
    public ImageView iv_image;
    PhotoViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.picture_item, parent, false));
        tv_title = (TextView)itemView.findViewById(R.id.title_tv);
        iv_image = (ImageView)itemView.findViewById(R.id.picture_img);

        //set her a click listener for the imageView

    }
}
