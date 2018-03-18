package christson.hackernews.util;

import android.content.Context;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import christson.hackernews.R;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppUtilTest
{
    @Mock
    Context context;

    @Test
    public void testParseTime()
    {
        long oneSecond = 1000;
        long oneMinute = 60 * oneSecond;
        long oneHour = 60 * oneMinute;
        long oneDay = 24 * oneHour;
        long tenSeconds = 10 * oneSecond;
        long tenMinutes = 10 * oneMinute;
        long tenHours = 10 * oneHour;
        long tenDays = 10 * oneDay;

        when(context.getString(R.string.second_ago)).thenReturn("second ago");
        String expected1 = AppUtil.parseTime(context, System.currentTimeMillis() - oneSecond);
        Assert.assertEquals(expected1, "1 second ago");

        when(context.getString(R.string.seconds_ago)).thenReturn("seconds ago");
        String expected2 = AppUtil.parseTime(context, System.currentTimeMillis() - tenSeconds);
        Assert.assertEquals(expected2, "10 seconds ago");

        when(context.getString(R.string.minute_ago)).thenReturn("minute ago");
        String expected3 = AppUtil.parseTime(context, System.currentTimeMillis() - oneMinute);
        Assert.assertEquals(expected3, "1 minute ago");

        when(context.getString(R.string.minutes_ago)).thenReturn("minutes ago");
        String expected4 = AppUtil.parseTime(context, System.currentTimeMillis() - tenMinutes);
        Assert.assertEquals(expected4, "10 minutes ago");

        when(context.getString(R.string.hour_ago)).thenReturn("hour ago");
        String expected5 = AppUtil.parseTime(context, System.currentTimeMillis() - oneHour);
        Assert.assertEquals(expected5, "1 hour ago");

        when(context.getString(R.string.hours_ago)).thenReturn("hours ago");
        String expected6 = AppUtil.parseTime(context, System.currentTimeMillis() - tenHours);
        Assert.assertEquals(expected6, "10 hours ago");

        when(context.getString(R.string.day_ago)).thenReturn("day ago");
        String expected7 = AppUtil.parseTime(context, System.currentTimeMillis() - oneDay);
        Assert.assertEquals(expected7, "1 day ago");

        when(context.getString(R.string.days_ago)).thenReturn("days ago");
        String expected8 = AppUtil.parseTime(context, System.currentTimeMillis() - tenDays);
        Assert.assertEquals(expected8, "10 days ago");
    }

    @Test
    public void testGetLimit()
    {
        int count = AppUtil.getRangeCount(7, 10, 5);
        Assert.assertEquals(count, 3);
    }
}
