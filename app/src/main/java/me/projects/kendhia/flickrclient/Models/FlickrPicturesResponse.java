package me.projects.kendhia.flickrclient.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KenDhia on 1/27/2017.
 */

public class FlickrPicturesResponse {

    List<Picture> pictures;

    public FlickrPicturesResponse() {
        pictures = new ArrayList<Picture>();
    }

    public static FlickrPicturesResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        FlickrPicturesResponse flickrPicturesResponse = gson.fromJson(response, FlickrPicturesResponse.class);
        return flickrPicturesResponse;
    }
}
