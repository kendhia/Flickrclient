package me.projects.kendhia.flickrclient;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.projects.kendhia.flickrclient.Fragments.MainFragment;
import me.projects.kendhia.flickrclient.Fragments.SinglePic;
import me.projects.kendhia.flickrclient.Models.Photo;

import static me.projects.kendhia.flickrclient.MainActivity.PHOTO_KEY;
import static me.projects.kendhia.flickrclient.MainActivity.PHOTO_TITLE;


public class PhotosAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
    List<Photo> photos;
    FragmentManager fragmentManager;
    boolean replace;

    public PhotosAdapter(FragmentManager fragmentManager, boolean replace) {
        photos = new ArrayList<>();
        this.fragmentManager = fragmentManager;
        this.replace = replace;
    }
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()), parent, fragmentManager);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        Photo photo= photos.get(position);
        holder.tv_title.setText(photos.get(position).getTitle());
        Picasso.with(holder.tv_title.getContext()).load(MainActivity.getImageUrl(photo, "m"))
                .placeholder(R.drawable.loading).into(holder.iv_image);

        //a click listener for the picture to start it in a full activity's screen
        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle args = new Bundle();
                args.putString(PHOTO_KEY, MainActivity.getImageUrl(photos.get(position), "m"));
                args.putString(PHOTO_TITLE, photos.get(position).getTitle());
                SinglePic singlePic = new SinglePic();
                singlePic.setArguments(args);

                if (replace) {
                    fragmentTransaction.replace(R.id.activity_main, singlePic);
                }
                else {
                    fragmentTransaction.add(R.id.activity_searchable, singlePic);
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public Photo getPhoto(int position) {
        return photos.get(position);
    }
    public void addItem(Photo photo) {
        photos.add(photo);
    }

}
