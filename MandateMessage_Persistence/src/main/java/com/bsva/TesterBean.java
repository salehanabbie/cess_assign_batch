package com.bsva;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class TesterBean
 */
@Stateless
@LocalBean
public class TesterBean implements TesterBeanRemote, TesterBeanLocal {

    /**
     * Default constructor. 
     */
    public TesterBean() {
        // TODO Auto-generated constructor stub
    }

}
