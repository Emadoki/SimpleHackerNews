package christson.hackernews.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import christson.hackernews.app.BaseItem;

public class Comment extends BaseItem
{
    @SerializedName("text")
    private String comment;

    @SerializedName("parent")
    private int parentId;
    @SerializedName("kids")
    private ArrayList<Integer> listOfCommentIds;

    public Comment()
    {
        super();

        this.comment = "";

        this.parentId = 0;

        this.listOfCommentIds = new ArrayList<Integer>();
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public int getParentId()
    {
        return parentId;
    }

    public void setParentId(int parentId)
    {
        this.parentId = parentId;
    }

    public ArrayList<Integer> getListOfCommentIds()
    {
        return listOfCommentIds;
    }

    public void setListOfCommentIds(ArrayList<Integer> listOfCommentIds)
    {
        this.listOfCommentIds = listOfCommentIds;
    }
}
