package christson.hackernews;

import java.util.ArrayList;

import christson.hackernews.entities.Comment;
import christson.hackernews.entities.Story;
import christson.hackernews.util.ITEM_TYPE;

public class Stub
{
    public static Story getStory(int i)
    {
        Story story = new Story();
        story.setId(i);
        story.setTitle("Title " + i);
        story.setScore(i);
        story.setUrl("Url " + i);
        story.setAuthor("Author " + i);
        story.setNumberOfComments(0);
        story.setListCommentIds(new ArrayList<Integer>());
        story.setTime(1521820800000L);
        story.setType(ITEM_TYPE.STORY);
        return story;
    }

    public static Comment getComment(int i)
    {
        Comment comment = new Comment();
        comment.setId(i);
        comment.setParentId(i);
        comment.setComment("Comment " + i);
        comment.setAuthor("Author " + i);
        comment.setTime(1521820800000L);
        comment.setType(ITEM_TYPE.COMMENT);
        comment.setListOfCommentIds(new ArrayList<Integer>());

        return comment;
    }
}
