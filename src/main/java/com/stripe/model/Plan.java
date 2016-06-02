package com.stripe.model;

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

import java.util.Map;

@Getter @Setter @EqualsAndHashCode(callSuper=false)
public class Plan extends APIResource implements MetadataStore<Plan>, HasId {
	String id;
	String object;
	Long amount;
	Long created;
	String currency;
	String interval;
	Integer intervalCount;
	Boolean livemode;
	Map<String, String> metadata;
	String nickname;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) ExpandableField<Product> product;

	/**
	 *  @deprecated Prefer using the product field (https://stripe.com/docs/upgrades#2018-02-05)
	 */
	@Deprecated
	String name;

	/**
	 *  @deprecated Prefer using the product field (https://stripe.com/docs/upgrades#2018-02-05)
	 */
	@Deprecated
	Integer trialPeriodDays;

	/**
	 *  @deprecated Prefer using the product field (https://stripe.com/docs/upgrades#2018-02-05)
	 */
	@Deprecated
	String statementDescription;

	/**
	 *  @deprecated Prefer using the product field (https://stripe.com/docs/upgrades#2018-02-05)
	 */
	@Deprecated
	String statementDescriptor;

	public String getProduct() {
		if (this.product == null) {
			return null;
		}
		return this.product.getId();
	}

	public void setProduct(String productID) {
		this.product = setExpandableFieldID(productID, this.product);

	}

	public Product getProductObject() {
		if (this.product == null) {
			return null;
		}
		return this.product.getExpanded();
	}

	public void setProductObject(Product product) {
		this.product = new ExpandableField<Product>(product.getId(), product);
	}

	public static Plan create(Map<String, Object> params)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return create(params, (RequestOptions) null);
	}

	public static Plan retrieve(String id) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, CardException,
			APIException {
		return retrieve(id, (RequestOptions) null);
	}

	public Plan update(Map<String, Object> params)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return update(params, (RequestOptions) null);
	}

	public DeletedPlan delete() throws AuthenticationException,
			InvalidRequestException, APIConnectionException, CardException,
			APIException {
		return delete((RequestOptions) null);
	}

	@Deprecated
	public static Plan create(Map<String, Object> params, String apiKey)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return create(params, RequestOptions.builder().setApiKey(apiKey).build());
	}

	public static Plan create(Map<String, Object> params, RequestOptions options)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return request(RequestMethod.POST, classURL(Plan.class), params, Plan.class, options);
	}

	@Deprecated
	public static Plan retrieve(String id, String apiKey)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return retrieve(id, RequestOptions.builder().setApiKey(apiKey).build());
	}

	public static Plan retrieve(String id, RequestOptions options)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return request(RequestMethod.GET, instanceURL(Plan.class, id), null, Plan.class, options);
	}

	@Deprecated
	public Plan update(Map<String, Object> params, String apiKey)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return update(params, RequestOptions.builder().setApiKey(apiKey).build());
	}

	public Plan update(Map<String, Object> params, RequestOptions options)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return request(RequestMethod.POST, instanceURL(Plan.class, this.id), params, Plan.class, options);
	}

	public static PlanCollection list(Map<String, Object> params)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return list(params, null);
	}

	public static PlanCollection list(Map<String, Object> params, RequestOptions options)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return requestCollection(classURL(Plan.class), params, PlanCollection.class, options);
	}

	@Deprecated
	public static PlanCollection all(Map<String, Object> params)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return list(params, null);
	}

	@Deprecated
	public static PlanCollection all(Map<String, Object> params, String apiKey)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return list(params, RequestOptions.builder().setApiKey(apiKey).build());
	}

	@Deprecated
	public static PlanCollection all(Map<String, Object> params, RequestOptions options)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return list(params, options);
	}

	@Deprecated
	public DeletedPlan delete(String apiKey) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, CardException,
			APIException {
		return delete(RequestOptions.builder().setApiKey(apiKey).build());
	}

	public DeletedPlan delete(RequestOptions options) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, CardException,
			APIException {
		return request(RequestMethod.DELETE, instanceURL(Plan.class, this.id), null, DeletedPlan.class, options);
	}
}
