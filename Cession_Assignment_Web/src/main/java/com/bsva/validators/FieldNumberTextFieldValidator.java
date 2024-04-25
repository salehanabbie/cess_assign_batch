/**
 * 
 */
package com.bsva.validators;

import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.eclipse.jetty.util.log.Log;

/**
 * @author MargaretM
 *
 */
public class FieldNumberTextFieldValidator implements INullAcceptingValidator<String> 
{

	private static final long serialVersionUID = 1L;

	@Override
	public void validate(IValidatable<String> fieldNumberTextField) 
	{
		final String fieldNumber = fieldNumberTextField.getValue();
		System.out.println("fieldNumber --> "+fieldNumber);
		if(fieldNumber != null)
		{
			try
			{
//				if(fieldNumber.length() > 13)
//				{
					Long.parseLong(fieldNumber);
//				}
//				else
//					Integer.parseInt(fieldNumber);
				
			}
			catch(NumberFormatException numberFormatException)
			{
				error(fieldNumberTextField, "Invalid content '"+fieldNumber+"'. Only numeric values are allowed!");
			}
		}
	}

	private void error(IValidatable<String> validatable, String message) 
	{
		ValidationError error = new ValidationError();
		error.setMessage(message);
		validatable.error(error);
	}
}
