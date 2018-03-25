package christson.hackernews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import christson.hackernews.R;
import christson.hackernews.entities.Story;
import christson.hackernews.interfaces.OnStoryAdapterClickListener;
import christson.hackernews.util.AppUtil;
import christson.hackernews.viewholders.StoryViewHolder;

public class RecyclerViewStoryAdapter extends RecyclerView.Adapter<StoryViewHolder>
{
    private Context context;
    private ArrayList<Story> listOfStory;
    private OnStoryAdapterClickListener clickListener;

    public RecyclerViewStoryAdapter(Context context, ArrayList<Story> list, OnStoryAdapterClickListener clickListener)
    {
        this.context = context;
        this.listOfStory = list;
        this.clickListener = clickListener;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(StoryViewHolder holder, int position)
    {
        Story story = listOfStory.get(position);
        holder.txtTitle.setText(story.getTitle());

        String time = AppUtil.parseTime(context, story.getTime());
        holder.txtTime.setText(time);

        // append text 'by' before author
        String author = getString(R.string.by) + " " + story.getAuthor();
        holder.txtAuthor.setText(author);

        // append text 'points' after score
        String points = story.getScore() + " " + getString(R.string.points);
        holder.txtScore.setText(points);

        // append text 'comments' after number
        String comments = story.getNumberOfComments() + " " + getString(R.string.comments);
        holder.txtComment.setText(comments);
    }

    @Override
    public int getItemCount()
    {
        return listOfStory.size();
    }

    private String getString(int resid)
    {
        return context.getString(resid);
    }

    public void setData(ArrayList<Story> list)
    {
        this.listOfStory.clear();
        this.listOfStory.addAll(list);
    }
}
