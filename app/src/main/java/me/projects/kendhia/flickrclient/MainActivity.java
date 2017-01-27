package me.projects.kendhia.flickrclient;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.projects.kendhia.flickrclient.Fragments.MainFragment;
import me.projects.kendhia.flickrclient.Models.Photo;


public class MainActivity extends AppCompatActivity {


    public static final String BASE_URL = "https://api.flickr.com/services/";
    public static final String FLICKR_PICS = "rest/?method=flickr.photos.getRecent&api_key=ca370d51a054836007519a00ff4ce59e&per_page=10&format=json&nojsoncallback=1&privacy_filter=1&page=";
    public static final String FLICKR_PIC_URL = "rest/?method=flickr.photos.getSizes&api_key=ca370d51a054836007519a00ff4ce59e&format=json&nojsoncallback=1&photo_id=";
    public static final String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main, new MainFragment());
        fragmentTransaction.commit();
    }


    public static String getImageUrl(Photo photo, String size) {
        String img_src = "https://farm"+photo.getFarm()+".staticflickr.com/"+photo.getServer()+"/"
                +photo.getId()+"_"+photo.getSecret()+"_" + size +".jpg";
        return img_src;
    }






}
