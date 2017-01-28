package me.projects.kendhia.flickrclient;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchableInfo;
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
    public static final String PHOTO_KEY = "photo";

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
        getNewPhotos(1);
    }


    public static void getNewPhotos(int mCurrent_page){
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
                for (Photo photo : photos) {
                    mPhotosAdapter.addItem(photo);
                    mPhotosAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<FlickrPicturesResponse> call, Throwable t) {
                //Add a snackbare to tell the user what happened
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
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
                intent.putExtra(SearchableActivity.SEARCH_SERVICE, query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
