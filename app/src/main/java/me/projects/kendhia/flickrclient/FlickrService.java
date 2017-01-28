package me.projects.kendhia.flickrclient;

import me.projects.kendhia.flickrclient.Models.FlickrPicturesResponse;
import me.projects.kendhia.flickrclient.Models.FlickrSinglePicRes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static me.projects.kendhia.flickrclient.MainActivity.FLICKR_PICS;
import static me.projects.kendhia.flickrclient.MainActivity.FLICKR_PIC_URL;



public interface FlickrService {

    @GET(FLICKR_PICS)
    Call<FlickrPicturesResponse> getPhotos(@Query("page") String page);

    @GET(FLICKR_PIC_URL)
    Call<FlickrSinglePicRes> getPhoto(@Query("photo_id") String pic_id);

    @GET(FLICKR_PICS)
    Call<FlickrPicturesResponse> searchPhotos(@Query("tags") String query);
}
