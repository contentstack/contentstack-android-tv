package mobile.contentstack.tv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.contentstack.sdk.Config;
import com.contentstack.sdk.Contentstack;
import com.contentstack.sdk.Entry;
import com.contentstack.sdk.Error;
import com.contentstack.sdk.Query;
import com.contentstack.sdk.QueryResult;
import com.contentstack.sdk.QueryResultsCallBack;
import com.contentstack.sdk.ResponseType;
import com.contentstack.sdk.Stack;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;



public class SplashActivity extends Activity {

    private String[] MOVIE_CATEGORY = { "new_release", "most_watched", "tv_shows", "top_rated" };
    private int COUNTER = 0;
    private LinkedHashMap<String, List<Movies>> LINKED_MOVIES = new LinkedHashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        for (String content_type: MOVIE_CATEGORY){ this.fetch(content_type); }

    }

    private Stack stackInstance(){
        Config config = new Config();
        config.setHost("app.contentstack.com");
        Stack stack = null;
        try {
            stack = Contentstack.stack(SplashActivity.this, BuildConfig.API_KEY, BuildConfig.ACCESS_TOKEN, BuildConfig.ENVIRONMENT, config);
        } catch (Exception error) {
            Toast.makeText(SplashActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        }

        return stack;
    }


    private void fetch(final String content_type_id) {
        Query query = stackInstance().contentType(content_type_id).query();
        query.find(new QueryResultsCallBack() {
                @Override
                public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                    if (error == null){
                        List<Entry> entries = queryresult.getResultObjects();
                        renderTheData(entries);
                    }else {
                        Toast.makeText(SplashActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error", content_type_id+" Content Type data loading Error: "+error.getErrorMessage());
                    }
                }
            });
    }


    private void renderTheData(List<Entry> result) {
        //MOVIES_LIST.clear();
        String trailorClip = "https://assets.contentstack.io/v3/assets/***REMOVED***/blt5cf3c44b2d2db7af/5d41481da25e75043a7d8ce8/the-lion-king-trailer-2_h1080p.mov";
        List<Movies> MOVIES_LIST = new ArrayList<>();
        for (Entry entry: result){


            try {

                String uid   = entry.toJSON().getString("uid");
                String title = entry.toJSON().getString("title");
                String description = entry.toJSON().getString("description");
                String cardpreview = entry.toJSON().getString("cardpreview");
                String studio = entry.toJSON().getString("studio");
                String trailor = entry.toJSON().getString("trailor");

                if (entry.toJSON().has("vids_file")){
                    JSONObject vedioTrailor = entry.toJSON().getJSONObject("vids_file");
                    if (vedioTrailor.has("url")){
                        trailorClip = vedioTrailor.optString("url");
                    }
                }

                MOVIES_LIST.add(new Movies(uid, title, description, cardpreview, trailorClip, trailor, studio));

            } catch (JSONException error) {
                Toast.makeText(SplashActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }

        LINKED_MOVIES.put(MOVIE_CATEGORY[COUNTER], MOVIES_LIST);
        Log.e("Counter: "+COUNTER, LINKED_MOVIES.toString());
        COUNTER = COUNTER+1;

        // Hold on till process of fetching entry completes
        if (COUNTER==4){
            Movies.setLinked_movies(LINKED_MOVIES);
            this.launchScreen();
        }
    }



    private void launchScreen(){

        final int TIMER_DELAY = 3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIMER_DELAY);
    }

}
