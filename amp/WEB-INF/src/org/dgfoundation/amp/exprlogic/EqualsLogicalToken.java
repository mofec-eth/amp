package org.dgfoundation.amp.exprlogic;

import org.dgfoundation.amp.ar.cell.CategAmountCell;

public class EqualsLogicalToken extends LogicalToken {
	protected String value;
	protected String type;
	public EqualsLogicalToken(String value,String type,boolean negation) {
		this.value=value;
		this.negation=negation;
		this.type=type;
	}
	
	public boolean evaluate(CategAmountCell c) {
		ret=value.equals(c.getMetaValueString(type));
		return super.evaluate(c);
	}
	
}
