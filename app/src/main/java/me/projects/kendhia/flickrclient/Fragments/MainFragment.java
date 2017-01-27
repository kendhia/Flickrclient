package me.projects.kendhia.flickrclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.projects.kendhia.flickrclient.EndlessRecyclerOnScrollListener;
import me.projects.kendhia.flickrclient.FlickrService;
import me.projects.kendhia.flickrclient.Models.FlickrPicturesResponse;
import me.projects.kendhia.flickrclient.Models.Photo;
import me.projects.kendhia.flickrclient.OnLoadMoreListener;
import me.projects.kendhia.flickrclient.PhotosAdapter;
import me.projects.kendhia.flickrclient.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.projects.kendhia.flickrclient.MainActivity.BASE_URL;


public class MainFragment extends Fragment implements OnLoadMoreListener {

    private View mView;
    private RecyclerView mRecyclerView;
    private PhotosAdapter mPhotosAdapter;
    private EndlessRecyclerOnScrollListener mEndlessScrollListener;

    private int mCurrent_page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.recyclerView);

        mPhotosAdapter = new PhotosAdapter();
        mRecyclerView.setAdapter(mPhotosAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mEndlessScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager, this);
        mRecyclerView.addOnScrollListener(mEndlessScrollListener);
        getNewPhotos();
        return mView;
    }

    void getNewPhotos(){
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

    @Override
    public void onLoadMore() {
        mCurrent_page++;
        getNewPhotos();
    }
}
