package christson.hackernews.util;

import android.content.Context;

import christson.hackernews.R;

public class AppUtil
{
    public static String parseTime(Context context, long millis)
    {
        long difference = System.currentTimeMillis() - millis;

        long secondMillis = 1000;
        long minuteMillis = secondMillis * 60;
        long hourMillis = minuteMillis * 60;
        long dayMillis = hourMillis * 24;

        long day = difference / dayMillis;
        difference = difference % dayMillis;
        long hour = difference / hourMillis;
        difference = difference % hourMillis;
        long minute = difference / minuteMillis;
        difference = difference % minuteMillis;
        long second = difference / secondMillis;

        String text = "";
        if (day > 0)
        {
            if (day == 1)
                text = day + " " + getString(context, R.string.day_ago);
            else
                text = day + " " + getString(context, R.string.days_ago);
        }
        else if (hour > 0)
        {
            if (hour == 1)
                text = hour + " " + getString(context, R.string.hour_ago);
            else
                text = hour + " " + getString(context, R.string.hours_ago);
        }
        else if (minute > 0)
        {
            if (minute == 1)
                text = minute + " " + getString(context, R.string.minute_ago);
            else
                text = minute + " " + getString(context, R.string.minutes_ago);
        }
        else if (second > 0)
        {
            if (second == 1)
                text = second + " " + getString(context, R.string.second_ago);
            else
                text = second + " " + getString(context, R.string.seconds_ago);
        }

        return text;
    }

    /**
     * Return the range count between from start index to end index.
     * @param start starting index
     * @param end end index
     * @param count desire counts
     * @return
     */
    public static int getRangeCount(int start, int end, int count)
    {
        // return the minimum value
        count = Math.min(Math.abs(end - start), count);
        return count < 0 ? 0 : count;
    }

    public static String getString(Context context, int resid)
    {
        return context.getString(resid);
    }
}
