package com.stripe.model;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.net.APIResource;
import com.stripe.net.RequestOptions;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter @EqualsAndHashCode(callSuper=false)
public class BalanceTransaction extends APIResource implements HasId {
	String id;
	String object;
	Long amount;
	Long availableOn;
	Long created;
	String currency;
	String description;
	Long fee;
	List<Fee> feeDetails;
	Integer net;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) ExpandableField<HasId> source;
	String status;
	String type;

	/**
	 * @deprecated Recent API versions no longer return this field (https://stripe.com/docs/upgrades#2017-01-27).
	 * Prefer listing all transfers with the `transfer_group` parameter: https://stripe.com/docs/api/java#list_transfers-transfer_group.
	 */
	@Deprecated
	@Getter(AccessLevel.NONE) TransferCollection sourcedTransfers;

	public TransferCollection getSourcedTransfers() {
		if (sourcedTransfers != null && sourcedTransfers.getURL() == null && getSource() != null) {
			sourcedTransfers.setURL(String.format("/v1/transfers?source_transaction=%s", getSource()));
		}
		return sourcedTransfers;
	}

	public String getSource() {
		if (this.source == null) {
			return null;
		}
		return this.source.getId();
	}

	public void setSource(String sourceID) {
		this.source = setExpandableFieldID(sourceID, this.source);
	}

	public HasId getSourceObject() {
		if (this.source == null) {
			return null;
		}
		return this.source.getExpanded();
	}

	public void setSourceObject(HasId o) {
		this.source = new ExpandableField<HasId>(o.getId(), o);
	}

	public <O extends HasId> O getSourceObjectAs() {
		if (this.source == null) {
			return null;
		}
		return (O) this.source.getExpanded();
 	}

	public static BalanceTransaction retrieve(String id) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, CardException,
			APIException {
		return retrieve(id, (RequestOptions) null);
	}

	@Deprecated
	public static BalanceTransaction retrieve(String id, String apiKey)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		String url = String.format("%s/%s/%s", Stripe.getApiBase(), "v1/balance/history", id);
		return retrieve(id, RequestOptions.builder().setApiKey(apiKey).build());
	}

	public static BalanceTransaction retrieve(String id, RequestOptions options)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		String url = String.format("%s/%s/%s", Stripe.getApiBase(), "v1/balance/history", id);
		return request(RequestMethod.GET, url, null, BalanceTransaction.class, options);
	}

	public static BalanceTransactionCollection list(Map<String, Object> params)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return list(params, null);
	}

	public static BalanceTransactionCollection list(Map<String, Object> params, RequestOptions options)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		String url = String.format("%s/%s", Stripe.getApiBase(), "v1/balance/history");
		return requestCollection(url, params, BalanceTransactionCollection.class, options);
	}

	@Deprecated
	public static BalanceTransactionCollection all(Map<String, Object> params)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return list(params, null);
	}

	@Deprecated
	public static BalanceTransactionCollection all(Map<String, Object> params, String apiKey)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return list(params, RequestOptions.builder().setApiKey(apiKey).build());
	}

	@Deprecated
	public static BalanceTransactionCollection all(Map<String, Object> params, RequestOptions options)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return list(params, options);
	}

}
