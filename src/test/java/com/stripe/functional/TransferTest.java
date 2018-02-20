package com.stripe.functional;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Transfer;
import com.stripe.model.TransferTransactionCollection;
import com.stripe.net.RequestOptions;
import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransferTest extends BaseStripeMockTest {
	@Test
	public void testTransferCreate() throws StripeException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", 100);
		params.put("currency", "usd");
		params.put("destination", "acct_123");

		Transfer transfer = Transfer.create(params);
		assertNotNull(transfer);
	}

	@Test
	public void testTransferRetrieve() throws StripeException {
		Transfer transfer = Transfer.retrieve("tr_123");
		assertNotNull(transfer);
	}

	@Test
	public void testTransferCancel() throws StripeException {
		Transfer transfer = Transfer.retrieve("tr_123");
		// TODO: test `cancel` URL to ensure it works as stripe-mock can't support this
		// transfer.cancel(RequestOptions.getDefault());
	}

	@Test
	public void testTransferUpdate() throws StripeException {
		Map<String, String> metadata = new HashMap<String, String>();
		metadata.put("key", "value");
		Map<String, Object> updateParams = new HashMap<String, Object>();
		updateParams.put("metadata", metadata);

		Transfer transfer = Transfer.retrieve("tr_123");
		Transfer updated = transfer.update(updateParams);
		assertNotNull(updated);
	}

	@Test
	public void testTransferList() throws StripeException {
		Map<String, Object> listParams = new HashMap<String, Object>();
		listParams.put("count", 1);
		List<Transfer> transfers = Transfer.all(listParams).getData();
		assertEquals(transfers.size(), 1);
	}

	@Test
	public void testTransferTransactions() throws StripeException {
		Transfer transfer = Transfer.retrieve("tr_123");
		// TODO: test `transactions` URL to ensure it works as stripe-mock can't support this
		// We also should deprecate this old method.
		//TransferTransactionCollection transactions = transfer.transactions(null, (RequestOptions) null);
		//assertNotNull(transactions);
	}
}
