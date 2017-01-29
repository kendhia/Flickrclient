package me.projects.kendhia.flickrclient;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.Toast;

import java.util.List;

import me.projects.kendhia.flickrclient.Models.FlickrPicturesResponse;
import me.projects.kendhia.flickrclient.Models.Photo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.projects.kendhia.flickrclient.MainActivity.BASE_URL;

public class SearchableActivity extends AppCompatActivity implements OnLoadMoreListener{

    RecyclerView mRecyclerView;
    PhotosAdapter mAdapter;
    private EndlessRecyclerOnScrollListener mEndlessScrollListener;
    private int mCurrent_page = 1;
    private String mQuery;
    private ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        String mQuery = getIntent().getStringExtra(SearchManager.QUERY);
        if (mQuery == null) {
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
        suggestions.saveRecentQuery(mQuery, null);

        mAdapter = new PhotosAdapter(getFragmentManager(), false);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mEndlessScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager, this);
        mRecyclerView.addOnScrollListener(mEndlessScrollListener);
        doMySearch(mQuery, this);


    }

    void doMySearch(String query,final Context context) {
        if (mCurrent_page == 1) {
            ringProgressDialog = ProgressDialog.show(context, "Please wait ...", "Downloading Images ...", true);
            ringProgressDialog.setCancelable(true);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlickrService flickrService = retrofit.create(FlickrService.class);

        flickrService.searchPhotos(String.valueOf(query), String.valueOf(mCurrent_page)).enqueue(new Callback<FlickrPicturesResponse>() {
            @Override
            public void onResponse(Call<FlickrPicturesResponse> call, Response<FlickrPicturesResponse> response) {
                FlickrPicturesResponse model = response.body();
                List<Photo> photos = model.getPhotos().getPhoto();
                if (ringProgressDialog != null){
                    ringProgressDialog.dismiss();
                }
                for (Photo photo : photos) {
                    mAdapter.addItem(photo);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FlickrPicturesResponse> call, Throwable t) {
                if (ringProgressDialog != null) {ringProgressDialog.dismiss();}
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();            }
        });
    }

    @Override
    public void onLoadMore() {
        mCurrent_page++;
        doMySearch(mQuery, getParent());
    }
}
