package me.projects.kendhia.flickrclient;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.projects.kendhia.flickrclient.Models.Photo;



public class PhotosAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
    List<Photo> photos;

    public PhotosAdapter() {
        photos = new ArrayList<>();
    }
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Photo photo= photos.get(position);
        holder.tv_title.setText(photos.get(position).getTitle());
        Picasso.with(holder.tv_title.getContext()).load(MainActivity.getImageUrl(photo, "m")).into(holder.iv_image);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void addItem(Photo photo) {
        photos.add(photo);
    }

}
