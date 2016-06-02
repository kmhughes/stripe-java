package com.stripe.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @EqualsAndHashCode(callSuper=false)
public final class VerificationFieldsDetails extends StripeObject {
	protected List<String> additional;
	protected List<String> minimum;
}
