package christson.hackernews.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import christson.hackernews.R;
import christson.hackernews.interfaces.OnStoryAdapterClickListener;

public class StoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public LinearLayout layoutContent;
    public LinearLayout layoutComment;
    public TextView txtTitle;
    public TextView txtScore;
    public TextView txtAuthor;
    public TextView txtComment;
    public TextView txtTime;

    private OnStoryAdapterClickListener clickListener;

    public StoryViewHolder(View view, OnStoryAdapterClickListener clickListener)
    {
        super(view);
        this.clickListener = clickListener;

        layoutContent = (LinearLayout) view.findViewById(R.id.layoutContent);
        layoutComment = (LinearLayout) view.findViewById(R.id.layoutComment);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtScore = (TextView) view.findViewById(R.id.txtScore);
        txtAuthor = (TextView) view.findViewById(R.id.txtAuthor);
        txtComment = (TextView) view.findViewById(R.id.txtComment);
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        // enable click events
        layoutContent.setOnClickListener(this);
        layoutComment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (clickListener == null)
            return;
        // trigger event callback
        switch (v.getId()){
            case R.id.layoutContent:
                clickListener.onContentClick(getLayoutPosition());
                break;
            case R.id.layoutComment:
                clickListener.onCommentClick(getLayoutPosition());
                break;
        }
    }
}
