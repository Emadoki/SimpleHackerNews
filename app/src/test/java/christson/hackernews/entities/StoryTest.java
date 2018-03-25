package christson.hackernews.entities;

import com.google.gson.Gson;

import org.junit.Test;

import christson.hackernews.util.ITEM_TYPE;

import static org.junit.Assert.*;

public class StoryTest
{
    @Test
    public void testDeserialize()
    {
        Story story = new Gson().fromJson(jsonSample(), Story.class);

        // only test for values that wont change over time
        assertEquals(story.getId(), 8863);
        assertEquals(story.getAuthor(), "dhouston");
        assertEquals(story.getTime(), 1175714200 * 1000L);
        assertEquals(story.getType(), ITEM_TYPE.STORY);
        assertEquals(story.getUrl(), "http://www.getdropbox.com/u/2/screencast.html");
    }

    private String jsonSample()
    {
        return "{\n" +
                "  \"by\" : \"dhouston\",\n" +
                "  \"descendants\" : 71,\n" +
                "  \"id\" : 8863,\n" +
                "  \"kids\" : [ 8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901, 8902, 8907, 8894, 8878, 8870, 8980, 8934, 8876 ],\n" +
                "  \"score\" : 111,\n" +
                "  \"time\" : 1175714200,\n" +
                "  \"title\" : \"My YC app: Dropbox - Throw away your USB drive\",\n" +
                "  \"type\" : \"story\",\n" +
                "  \"url\" : \"http://www.getdropbox.com/u/2/screencast.html\"\n" +
                "}";
    }
}