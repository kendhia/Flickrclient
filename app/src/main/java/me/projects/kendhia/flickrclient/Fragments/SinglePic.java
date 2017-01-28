package me.projects.kendhia.flickrclient.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.projects.kendhia.flickrclient.MainActivity;
import me.projects.kendhia.flickrclient.Models.Photo;
import me.projects.kendhia.flickrclient.PhotosAdapter;
import me.projects.kendhia.flickrclient.R;

import static me.projects.kendhia.flickrclient.MainActivity.PHOTO_KEY;


public class SinglePic extends Fragment {

    View rootView;
    ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_single_pic, container, false);
        String photoURL = getArguments().getString(PHOTO_KEY);
        mImageView = (ImageView)rootView.findViewById(R.id.picture_img);
        Picasso.with(mImageView.getContext()).load(photoURL).into(mImageView);
        return rootView;
    }

}
