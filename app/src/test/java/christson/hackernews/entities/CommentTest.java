package christson.hackernews.entities;

import com.google.gson.Gson;

import org.junit.Test;

import christson.hackernews.util.ITEM_TYPE;

import static org.junit.Assert.*;

public class CommentTest
{
    @Test
    public void testDeserialize()
    {
        Comment comment = new Gson().fromJson(jsonSample(), Comment.class);

        // only test for values that wont change over time
        assertEquals(comment.getId(), 2921983);
        assertEquals(comment.getAuthor(), "norvig");
        assertEquals(comment.getParentId(), 2921506);
        assertEquals(comment.getTime(), 1314211127 * 1000L);
        assertEquals(comment.getType(), ITEM_TYPE.COMMENT);
    }

    private String jsonSample()
    {
        return "{\n" +
                "  \"by\" : \"norvig\",\n" +
                "  \"id\" : 2921983,\n" +
                "  \"kids\" : [ 2922097, 2922429, 2924562, 2922709, 2922573, 2922140, 2922141 ],\n" +
                "  \"parent\" : 2921506,\n" +
                "  \"text\" : \"Aw shucks, guys ... you make me blush with your compliments.<p>Tell you what, Ill make a deal: I'll keep writing if you keep reading. K?\",\n" +
                "  \"time\" : 1314211127,\n" +
                "  \"type\" : \"comment\"\n" +
                "}";
    }
}