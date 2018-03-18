package christson.hackernews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import christson.hackernews.R;
import christson.hackernews.entities.Comment;
import christson.hackernews.util.AppUtil;
import christson.hackernews.viewholders.CommentViewHolder;

public class RecyclerViewCommentAdapter extends RecyclerView.Adapter<CommentViewHolder>
{
    private Context context;
    private ArrayList<Comment> listOfComment;

    public RecyclerViewCommentAdapter(Context context, ArrayList<Comment> list)
    {
        this.context = context;
        this.listOfComment = list;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position)
    {
        Comment comment = listOfComment.get(position);

        holder.txtComment.setText(Html.fromHtml(comment.getComment()));
        holder.txtAuthor.setText(comment.getAuthor());

        String time = AppUtil.parseTime(context, comment.getTime());
        holder.txtTime.setText(time);
    }

    @Override
    public int getItemCount()
    {
        return listOfComment.size();
    }
}
