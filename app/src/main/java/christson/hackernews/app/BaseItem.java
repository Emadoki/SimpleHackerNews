package christson.hackernews.app;

import com.google.gson.annotations.SerializedName;

import christson.hackernews.util.ITEM_TYPE;

public class BaseItem
{
    @SerializedName("by")
    private String author;

    @SerializedName("id")
    private int id;
    @SerializedName("time")
    private long time;
    @SerializedName("type")
    private ITEM_TYPE type;

    public BaseItem()
    {
        this.author = "";

        this.id = 0;
        this.time = 0;

        this.type = null;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public long getTime()
    {
        // convert epoch timestamp to datetime milliseconds by multiplying 1000
        return time * 1000L;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public ITEM_TYPE getType()
    {
        return type;
    }

    public void setType(ITEM_TYPE type)
    {
        this.type = type;
    }
}
