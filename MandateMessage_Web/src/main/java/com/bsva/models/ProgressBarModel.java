package com.bsva.models;

public abstract  class ProgressBarModel  
{
	private final int progress;
	private final String message;
	
	public ProgressBarModel(int progress)
	{
		this.progress = progress;
		message = null;
	}

	public ProgressBarModel(int progress,String message)
	{
		this.progress = progress;
		this.message = message;
	}


	public boolean isDone ()
	{
		return progress>=100;
	}
	
	public int getProgress()
	{
		return progress;
	}
	
	public String getProgressMessage()
	{
		return message;
	}
	
}
