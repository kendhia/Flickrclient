package me.projects.kendhia.flickrclient;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.widget.Toast;

import java.util.List;

import me.projects.kendhia.flickrclient.Fragments.MainFragment;
import me.projects.kendhia.flickrclient.Models.FlickrPicturesResponse;
import me.projects.kendhia.flickrclient.Models.Photo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    public static final String BASE_URL = "https://api.flickr.com/services/";
    public static final String FLICKR_PICS = "rest/?method=flickr.photos.getRecent&api_key=ca370d51a054836007519a00ff4ce59e&per_page=10&format=json&nojsoncallback=1&privacy_filter=1&page=";
    public static final String FLICKR_PIC_URL = "rest/?method=flickr.photos.getSizes&api_key=ca370d51a054836007519a00ff4ce59e&format=json&nojsoncallback=1&photo_id=";
    public static final String PHOTO_KEY = "photo_url";
    public static final String PHOTO_TITLE = "photo_title";
    private static ProgressDialog ringProgressDialog;


    public static PhotosAdapter mPhotosAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhotosAdapter = new PhotosAdapter(getFragmentManager(), true);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main, new MainFragment());
        fragmentTransaction.commit();
        getNewPhotos(1, this);
    }


    public static void getNewPhotos(int mCurrent_page,final Context context){
        if (mCurrent_page == 1) {
            ringProgressDialog = ProgressDialog.show(context, "Please wait ...", "Downloading Images ...", true);
            ringProgressDialog.setCancelable(true);
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlickrService flickrService = retrofit.create(FlickrService.class);

        flickrService.getPhotos(String.valueOf(mCurrent_page)).enqueue(new Callback<FlickrPicturesResponse>() {
            @Override
            public void onResponse(Call<FlickrPicturesResponse> call, Response<FlickrPicturesResponse> response) {
                FlickrPicturesResponse model = response.body();
                List<Photo> photos = model.getPhotos().getPhoto();
                if (ringProgressDialog != null) {ringProgressDialog.dismiss();}
                for (Photo photo : photos) {
                    mPhotosAdapter.addItem(photo);
                    mPhotosAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FlickrPicturesResponse> call, Throwable t) {
                if (ringProgressDialog != null) {ringProgressDialog.dismiss();}
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();
            }
        });
    }


    public static String getImageUrl(Photo photo, String size) {
        String img_src = "https://farm"+photo.getFarm()+".staticflickr.com/"+photo.getServer()+"/"
                +photo.getId()+"_"+photo.getSecret()+"_" + size +".jpg";
        return img_src;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return super.onSearchRequested(searchEvent);
    }
}
