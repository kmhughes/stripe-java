package com.stripe.model;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PlanTest extends BaseStripeMockTest {
    @Test
    public void testDeserialize() throws Exception {
        String data = getFixture("/v1/plans/gold");
        Plan object = APIResource.GSON.fromJson(data, Plan.class);
        assertNotNull(object);
        assertNotNull(object.getId());
    }

    @Test
    public void testDeserializeWithExpansions() throws Exception {
        // Specify expansions manually because it's a nested resource
        String[] expansions = { "product" };
        String planData = getFixture("/v1/plans/gold", expansions);
        String data = getDataAt(planData, "product");
        Product object = APIResource.GSON.fromJson(data, Product.class);
        assertNotNull(object);
        assertNotNull(object.getName());
    }
}
