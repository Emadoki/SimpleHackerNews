package christson.hackernews.adapters;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import christson.hackernews.Stub;
import christson.hackernews.activities.MainActivity;
import christson.hackernews.entities.Story;
import christson.hackernews.viewholders.StoryViewHolder;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewStoryAdapterTest
{
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class);

    public RecyclerViewStoryAdapter adapter;

    @Before
    public void setup()
    {
        adapter = new RecyclerViewStoryAdapter(rule.getActivity(), getList(), null);
    }

    @Test
    public void testOnCreateViewHolder()
    {
        RecyclerView.ViewHolder holder = createViewHolder();
        assertTrue(holder instanceof StoryViewHolder);
    }

    @Test
    public void testOnBindViewHolder()
    {
        StoryViewHolder holder = (StoryViewHolder) createViewHolder();
        adapter.onBindViewHolder(holder, 0);
        assertTrue(compare(holder, 0));

        adapter.onBindViewHolder(holder, 1);
        assertFalse(compare(holder, 0));

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

    private boolean compare(StoryViewHolder holder, int position)
    {
        String expected = holder.txtTitle.getText().toString();
        String actual = getList().get(position).getTitle();
        return expected.equals(actual);
    }

    /**
     * Generate 3 samples story
     * @return
     */
    private ArrayList<Story> getList()
    {
        ArrayList<Story> list = new ArrayList<>();
        for (int i = 0; i < 3; i ++)
            list.add(Stub.getStory(i));

        return list;
    }
}