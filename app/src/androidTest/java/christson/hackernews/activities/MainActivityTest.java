package christson.hackernews.activities;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import christson.hackernews.R;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest
{
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testToolbarTitle()
    {
        onView(withId(R.id.toolbar)).equals(rule.getActivity().getString(R.string.app_name));
    }

    @Test
    public void testRecyclerView()
    {
        try
        {
            Thread.sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, clickChild(R.id.layoutComment)));
    }

    private static ViewAction clickChild(final int id)
    {
        return new ViewAction()
        {
            @Override
            public Matcher<View> getConstraints()
            {
                return null;
            }

            @Override
            public String getDescription()
            {
                return "";
            }

            @Override
            public void perform(UiController uiController, View view)
            {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}