package com.bsva.commons.model;

/**
 * @author SalehaR
 *
 */
public enum RealTimeServices 
{
	TT1Manin,
	TT3Manin,
	TT1Manam,
	TT3Manam,
	TT1Mancn;
	
	public String value() {
        return name();
    }

    public static RealTimeServices fromValue(String v) {
        return valueOf(v);
    }
}
