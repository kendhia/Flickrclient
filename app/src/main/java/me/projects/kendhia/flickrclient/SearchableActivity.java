package me.projects.kendhia.flickrclient;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import me.projects.kendhia.flickrclient.Models.FlickrPicturesResponse;
import me.projects.kendhia.flickrclient.Models.Photo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.projects.kendhia.flickrclient.MainActivity.BASE_URL;

public class SearchableActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    PhotosAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        String query = getIntent().getStringExtra(SearchableActivity.SEARCH_SERVICE);
        if (query == null) {
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);

        mAdapter = new PhotosAdapter(getFragmentManager());
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        doMySearch(query);

    }

    void doMySearch(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlickrService flickrService = retrofit.create(FlickrService.class);

        flickrService.searchPhotos(String.valueOf(query)).enqueue(new Callback<FlickrPicturesResponse>() {
            @Override
            public void onResponse(Call<FlickrPicturesResponse> call, Response<FlickrPicturesResponse> response) {
                FlickrPicturesResponse model = response.body();
                List<Photo> photos = model.getPhotos().getPhoto();
                for (Photo photo : photos) {
                    mAdapter.addItem(photo);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FlickrPicturesResponse> call, Throwable t) {
                //Add a snackbare to tell the user what happened
            }
        });
    }

}
