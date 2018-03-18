package christson.hackernews.util;

import com.google.gson.annotations.SerializedName;

public enum ITEM_TYPE
{
    @SerializedName("story")
    STORY,
    @SerializedName("comment")
    COMMENT,
    @SerializedName("job")
    JOB,
    @SerializedName("poll")
    POLL,
    @SerializedName("pollopt")
    POLLOPT
}
