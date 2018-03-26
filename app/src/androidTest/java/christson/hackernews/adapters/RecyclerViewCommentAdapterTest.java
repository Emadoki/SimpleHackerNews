package christson.hackernews.adapters;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import christson.hackernews.Stub;
import christson.hackernews.activities.CommentActivity;
import christson.hackernews.entities.Comment;
import christson.hackernews.viewholders.CommentViewHolder;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewCommentAdapterTest
{
    @Rule
    public ActivityTestRule<CommentActivity> rule = new ActivityTestRule<CommentActivity>(CommentActivity.class);

    public RecyclerViewCommentAdapter adapter;

    @Before
    public void setup()
    {
        adapter = new RecyclerViewCommentAdapter(rule.getActivity(), getList());
    }

    @Test
    public void testOnCreateViewHolder()
    {
        RecyclerView.ViewHolder holder = createViewHolder();
        assertTrue(holder instanceof CommentViewHolder);
    }

    @Test
    public void testOnBindViewHolder()
    {
        CommentViewHolder holder = (CommentViewHolder) createViewHolder();

        adapter.onBindViewHolder(holder, 0);
        assertTrue(compare(holder, 0));

        adapter.onBindViewHolder(holder, 1);
        assertFalse(compare(holder, 2));

        adapter.onBindViewHolder(holder, 2);
        assertTrue(compare(holder, 2));
    }

    @Test
    public void testGetItemCount()
    {
        assertEquals(adapter.getItemCount(), 3);
    }

    private RecyclerView.ViewHolder createViewHolder()
    {
        return adapter.onCreateViewHolder(new RelativeLayout(rule.getActivity()), 0);
    }

    /**
     * Check if {@link CommentViewHolder#txtComment} text is equals to {@link Stub#getComment(int)}
     * @param holder
     * @param position index in list
     * @return
     */
    private boolean compare(CommentViewHolder holder, int position)
    {
        String expected = holder.txtComment.getText().toString();
        String actual = getList().get(position).getHtmlComment().toString();
        return expected.equals(actual);
    }

    private ArrayList<Comment> getList()
    {
        ArrayList<Comment> list = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            list.add(Stub.getComment(i));
        return list;
    }
}