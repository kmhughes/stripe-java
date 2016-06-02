package com.stripe.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @EqualsAndHashCode(callSuper=false)
public final class VerificationFields extends StripeObject {
	protected VerificationFieldsDetails individual;
	protected VerificationFieldsDetails company;
}
