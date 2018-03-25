package christson.hackernews.api;

import org.junit.Test;

import java.util.ArrayList;

import christson.hackernews.entities.Comment;
import christson.hackernews.entities.Story;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

public class ApiEndPointTest
{
    @Test
    public void testGetListOfNewStories()
    {
        // test api call
        TestObserver<ArrayList<Integer>> observer = new TestObserver<>();
        ApiClient.create().getListOfNewStories().subscribe(observer);

        assertNotNull(observer.values());
    }

    @Test
    public void testGetStory()
    {
        // test api call
        TestObserver<Story> observer = new TestObserver<Story>();
        ApiClient.create().getStory(0).subscribe(observer);

        observer.onComplete();
        //assertNotNull(observer.values());
        //assertEquals(observer.values().get(0).getId(), 16654080);
    }

    @Test
    public void testGetComment()
    {
        // test api call
        TestObserver<Comment> observer = new TestObserver<Comment>();
        ApiClient.create().getComment(16657130).subscribe(observer);

        assertNotNull(observer.values());
        assertEquals(observer.values().get(0).getId(), 16654080);
    }
}