package christson.hackernews.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import christson.hackernews.R;

public class CommentViewHolder extends RecyclerView.ViewHolder
{
    public TextView txtComment;
    public TextView txtAuthor;
    public TextView txtTime;

    public CommentViewHolder(View view)
    {
        super(view);
        txtComment = (TextView) view.findViewById(R.id.txtComment);
        txtAuthor = (TextView) view.findViewById(R.id.txtAuthor);
        txtTime = (TextView) view.findViewById(R.id.txtTime);
    }
}
