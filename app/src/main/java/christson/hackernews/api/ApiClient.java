package christson.hackernews.api;

import java.util.ArrayList;

import christson.hackernews.entities.Comment;
import christson.hackernews.entities.Story;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{
    private static final String BASE_URL = "https://hacker-news.firebaseio.com/v0/";

    public static ApiEndPoint create()
    {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiEndPoint.class);
    }
}
