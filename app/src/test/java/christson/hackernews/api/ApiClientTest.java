package christson.hackernews.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApiClientTest
{
    @Test
    public void testCreate()
    {
        assertNotNull(ApiClient.create());
    }
}