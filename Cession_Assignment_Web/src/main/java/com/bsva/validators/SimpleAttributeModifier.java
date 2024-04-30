package com.bsva.validators;



import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.Model;

public  class SimpleAttributeModifier  extends AttributeModifier
{


	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;

	public SimpleAttributeModifier(String event, String msg) {
		super(event, new Model(msg));
	}

	protected String newValue(final String currentValue, final String replacementValue) {
		String prefix = "var conf = confirm('" + replacementValue + "'); " +
			"if (!conf) return false; ";
		String result = prefix;
		if (currentValue != null) {
			result = prefix + currentValue;
		}
		return result;
	}
	
	
	
}
