package christson.hackernews.api;

import java.util.ArrayList;

import christson.hackernews.entities.Comment;
import christson.hackernews.entities.Story;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndPoint
{
    @GET("topstories.json")
    Single<ArrayList<Integer>> getListOfNewStories();

    @GET("item/{id}.json")
    Observable<Story> getStory(@Path("id") int id);

    @GET("item/{id}.json")
    Observable<Comment> getComment(@Path("id") int id);
}
