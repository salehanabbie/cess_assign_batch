package com.bsva.interfaces;

import javax.ejb.Remote;

/**
 * @author SalehaR
 *
 */
@Remote
public interface PropertyUtilRemote 
{
	public String getPropValue(String key);
}
