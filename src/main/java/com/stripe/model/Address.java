package com.stripe.model;

import com.stripe.net.APIResource;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter @EqualsAndHashCode(callSuper=false)
public final class Address extends StripeObject {
	protected String city;
	protected String country;
	protected String line1;
	protected String line2;
	protected String postalCode;
	protected String state;

	public Address setCity(String city) {
		this.city = city;
		return this;
	}

	public Address setCountry(String country) {
		this.country = country;
		return this;
	}

	public Address setLine1(String line1) {
		this.line1 = line1;
		return this;
	}

	public Address setLine2(String line2) {
		this.line2 = line2;
		return this;
	}

	public Address setPostalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public Address setState(String state) {
		this.state = state;
		return this;
	}
}
