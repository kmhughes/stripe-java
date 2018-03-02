package com.stripe.model;

import com.stripe.BaseStripeTest;
import com.stripe.exception.StripeException;
import com.stripe.net.APIResource;
import com.stripe.net.LiveStripeResponseGetter;
import org.junit.After;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class UsageRecordTest extends BaseStripeTest {

    @Before
    public void mockStripeResponseGetter() {
        APIResource.setStripeResponseGetter(networkMock);
    }

    @After
    public void unmockStripeResponseGetter() {
        /* This needs to be done because tests aren't isolated in Java */
        APIResource.setStripeResponseGetter(new LiveStripeResponseGetter());
    }

    @Test
    public void testCreate() throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("quantity", 1000);
        long unixTime = System.currentTimeMillis() / 1000L;
        params.put("timestamp", unixTime);
        params.put("livemode", true);

        UsageRecord ur = UsageRecord.create("si_abc", params);

        verifyPost(UsageRecord.class, "https://api.stripe.com/v1/subscription_items/si_abc/usage_records", params);
        verifyNoMoreInteractions(networkMock);
    }
}
