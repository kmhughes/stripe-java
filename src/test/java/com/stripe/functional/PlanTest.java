package com.stripe.functional;

import com.stripe.BaseStripeMockTest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.DeletedPlan;
import com.stripe.model.Plan;
import com.stripe.model.PlanCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlanTest extends BaseStripeMockTest {
	@Test
	public void testPlanCreate() throws StripeException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", 1);
		params.put("currency", "usd");
		params.put("id", "sapphire-elite");
		params.put("interval", "month");
		params.put("name", "Sapphire Elite");
		Plan plan = Plan.create(params);
		assertNotNull(plan);
	}

	@Test
	public void testPlanRetrieve() throws StripeException {
		Plan plan = Plan.retrieve("gold");
		assertNotNull(plan);
	}

	@Test
	public void testPlanUpdate() throws StripeException {
		Plan plan = Plan.retrieve("gold");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Updated Name");
		Plan updatedPlan = plan.update(params);
		assertNotNull(updatedPlan);
	}

	@Test
	public void testPlanDelete() throws StripeException {
		Plan plan = Plan.retrieve("gold");
		DeletedPlan deletedPlan = plan.delete();
		assertNotNull(deletedPlan);
	}

	@Test
	public void testPlanList() throws StripeException {
		PlanCollection plans = Plan.all(null);
		assertNotNull(plans);
		assertEquals(1, plans.getData().size());
	}
}
