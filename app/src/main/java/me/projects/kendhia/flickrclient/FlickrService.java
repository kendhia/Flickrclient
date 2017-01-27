package me.projects.kendhia.flickrclient;

import me.projects.kendhia.flickrclient.Models.FlickrPicturesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

import static me.projects.kendhia.flickrclient.MainActivity.FLICKR_PICS;
import static me.projects.kendhia.flickrclient.MainActivity.FLICKR_SEARCH;

/**
 * Created by KenDhia on 1/27/2017.
 */

public interface FlickrService {

    @GET(FLICKR_PICS)
    Call<FlickrPicturesResponse> getPhotos();
}
