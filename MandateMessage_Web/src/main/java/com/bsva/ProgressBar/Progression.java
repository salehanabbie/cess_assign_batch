package com.bsva.ProgressBar;

import org.apache.wicket.model.AbstractReadOnlyModel;

import com.bsva.models.ProgressBarModel;

public  abstract class Progression  extends AbstractReadOnlyModel<ProgressBarModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public final ProgressBarModel getObject()
		{
			return getProgression();
		}

	
	public abstract ProgressBarModel getProgression();
}
