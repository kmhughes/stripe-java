package com.stripe.functional;

import com.stripe.BaseStripeMockTest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

public class TokenTest extends BaseStripeMockTest {
	@Test
	public void testTokenCreate() throws StripeException {
		Map<String, Object> cardParams = new HashMap<String, Object>();
		cardParams.put("number", "4242424242424242"); // Test token creation so needs PAN.
		cardParams.put("exp_month", 12);
		cardParams.put("exp_year", getYear());
		cardParams.put("cvc", "123");

		Map<String, Object> tokenParams = new HashMap<String, Object>();
		tokenParams.put("card", cardParams);

		Token token = Token.create(tokenParams);
		assertNotNull(token);
	}

	@Test
	public void testTokenRetrieve() throws StripeException {
		Token token = Token.retrieve("tok_123");
		assertNotNull(token);
	}
}
