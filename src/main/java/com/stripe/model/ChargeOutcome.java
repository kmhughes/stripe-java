package com.stripe.model;

import com.stripe.net.APIResource;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode(callSuper=false)
public class ChargeOutcome extends APIResource {
	protected String networkStatus;
	protected String reason;
	protected String riskLevel;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) protected ExpandableField<ChargeOutcomeRule> rule;
	protected String sellerMessage;
	protected String type;

	@Deprecated
	public ChargeOutcomeRule getRule() {
		if (this.rule == null) {
			return null;
		}
		return this.rule.getExpanded();
	}

	public String getRuleId() {
		if (this.rule == null) {
			return null;
		}
		return this.rule.getId();
	}

	public ChargeOutcomeRule getRuleObject() {
		if (this.rule == null) {
			return null;
		}
		return this.rule.getExpanded();
	}

	@Deprecated
	public void setRule(ChargeOutcomeRule rule) {
		this.rule = new ExpandableField<ChargeOutcomeRule>(rule.getId(), rule);
	}

	public void setRuleId(String ruleId) {
		this.rule = setExpandableFieldID(ruleId, this.rule);
	}

	public void setRuleObject(ChargeOutcomeRule rule) {
		this.rule = new ExpandableField<ChargeOutcomeRule>(rule.getId(), rule);
	}
}
