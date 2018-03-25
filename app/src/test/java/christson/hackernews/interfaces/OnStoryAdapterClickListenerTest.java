package christson.hackernews.interfaces;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OnStoryAdapterClickListenerTest
{
    @Test
    public void testOnContentClick()
    {
        OnStoryAdapterClickListener listener = mock(OnStoryAdapterClickListener.class);
        listener.onContentClick(0);
        verify(listener).onContentClick(0);
    }

    @Test
    public void testOnCommentClick()
    {
        OnStoryAdapterClickListener listener = mock(OnStoryAdapterClickListener.class);
        listener.onCommentClick(0);
        verify(listener).onCommentClick(0);
    }

}