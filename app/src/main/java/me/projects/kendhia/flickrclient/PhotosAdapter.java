package me.projects.kendhia.flickrclient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.projects.kendhia.flickrclient.Models.Photo;

/**
 * Created by KenDhia on 1/27/2017.
 */

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
        holder.tv_title.setText(photos.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void addItem(Photo photo) {
        photos.add(photo);
    }
}
