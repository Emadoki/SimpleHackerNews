package christson.hackernews.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import christson.hackernews.app.BaseItem;
import christson.hackernews.util.ITEM_TYPE;

public class Story extends BaseItem
{
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;

    @SerializedName("score")
    private int score; // number of votes
    @SerializedName("descendants")
    private int numberOfComments;

    @SerializedName("kids")
    private ArrayList<Integer> listCommentIds;

    public Story()
    {
        super();
        this.title = "";
        this.url = "";

        this.score = 0;
        this.numberOfComments = 0;

        this.listCommentIds = new ArrayList<Integer>();
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getNumberOfComments()
    {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments)
    {
        this.numberOfComments = numberOfComments;
    }

    public ArrayList<Integer> getListCommentIds()
    {
        return listCommentIds;
    }

    public void setListCommentIds(ArrayList<Integer> listCommentIds)
    {
        this.listCommentIds = listCommentIds;
    }
}
