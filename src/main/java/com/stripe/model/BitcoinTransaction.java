package com.stripe.model;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.net.APIResource;
import com.stripe.net.RequestOptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;

@Getter @Setter @EqualsAndHashCode(callSuper=false)
public class BitcoinTransaction extends APIResource implements HasId {
	String id;
	Long amount;
	Long bitcoinAmount;
	Long created;
	String currency;
	String customer;
	String receiver;
}
