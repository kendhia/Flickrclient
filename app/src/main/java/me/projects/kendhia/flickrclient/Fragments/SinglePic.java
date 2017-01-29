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
import static me.projects.kendhia.flickrclient.MainActivity.PHOTO_TITLE;


public class SinglePic extends Fragment {

    View rootView;
    ImageView mImageView;
    TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_single_pic, container, false);
        String photoURL = getArguments().getString(PHOTO_KEY);
        String photoTitle = getArguments().getString(PHOTO_TITLE);
        mImageView = (ImageView)rootView.findViewById(R.id.picture_img);
        mTextView = (TextView)rootView.findViewById(R.id.title_tv);
        mTextView.setText(photoTitle);
        Picasso.with(mImageView.getContext()).load(photoURL).placeholder(R.drawable.loading).into(mImageView);
        return rootView;
    }

}
