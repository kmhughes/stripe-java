package com.stripe.model;

import com.stripe.net.APIResource;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode(callSuper=false)
public class OrderItem extends APIResource {
	String object;
	Long amount;
	String currency;
	String description;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) ExpandableField<HasId> parent;
	Integer quantity;
	String type;

	public String getParent() {
		if (this.parent == null) {
			return null;
		}
		return this.parent.getId();
	}

	public void setParent(String parentID) {
		this.parent = setExpandableFieldID(parentID, this.parent);
	}

	public HasId getParentObject() {
		if (this.parent == null) {
			return null;
		}
		return this.parent.getExpanded();
	}

	public void setParentObject(HasId o) {
		this.parent = new ExpandableField<HasId>(o.getId(), o);
	}

	public <O extends HasId> O getParentObjectAs() {
		if (this.parent == null) {
			return null;
		}
		return (O) this.parent.getExpanded();
 	}
}
