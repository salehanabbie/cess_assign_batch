package com.bsva.panels.systemInfo;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.time.Duration;
import org.odlabs.wiquery.ui.progressbar.ProgressBar;
/**
 * 
 * @author DimakatsoN
 *
 */

public class SystemMonitorPanel  extends Panel implements Serializable{
	
public Form form;
	
	private static final long serialVersionUID = 1L;

	public static String id;
	
    private ProgressBar progressBarVmemory;
    private ProgressBar progressBarPsize;
    private ProgressBar progressBarSwapSpace;
    private ProgressBar progressBarCPUload;
    private ProgressBar progressBarCPUtime;
    private ProgressBar progressBarSystemLoad;
    private ProgressBar progressBarTotPmemsize;
    private ProgressBar progressBarTotSwap;
	

    private Label labelprogressBarVmemory;
    private Label labelprogressBarPsize;
    private Label labelprogressBarSwapSpace;
    private Label labelprogressBarCPUload;
    private Label labelprogressBarCPUtime ;
    private Label labelprogressBarSystemLoad ;
    private Label labelprogressBarTotPmemsize ;
    private Label labelprogressBarTotSwap;
    
    
    private Model<String> progressBarVmemoryMdl;
    private Model<String> progressBarPsizeMdl;
    private Model<String>progressBarSwapSpaceMdl;
    private Model<String> progressBarCPUloadMdl ;
    private Model<String> progressBarCPUtimeMdl;
    private Model<String> progressBarSystemLoadMdl;
    private Model<String> progressBarTotPmemsizeMdl ;
    private Model<String> progressBarTotSwapMdl;
    
    
    
  

	public SystemMonitorPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		this.id = id;
		initializeComponents();	
		printUsage();
	}


	private void initializeComponents() {
		// TODO Auto-generated method stub
		form = new Form("form");

		 ArrayList<Object> array = printUsage();
		    
		    double mb = 1024*1024;
		    int gb = 1024*1024*1024;
		    File diskPartition = new File("C:");
		    diskPartition.getTotalSpace();
		    
		    DecimalFormat df = new DecimalFormat("#0.00");
		 
		   
		    
		     progressBarVmemoryMdl = Model.of(df.format(Double.valueOf(array.get(0).toString())/mb) + "MB");
		     progressBarPsizeMdl = Model.of(df.format(Double.valueOf(array.get(1).toString())/mb) + "MB");
		    progressBarSwapSpaceMdl = Model.of(df.format(Double.valueOf(array.get(2).toString())/mb) + "MB") ;
		    progressBarCPUloadMdl = Model.of(df.format(Double.valueOf(array.get(3).toString())/mb) + "MB");
		     progressBarCPUtimeMdl = Model.of(array.get(4).toString());
		     progressBarSystemLoadMdl = Model.of(df.format(Double.valueOf(array.get(5).toString())/mb) + "MB");
		     progressBarTotPmemsizeMdl = Model.of(df.format(Double.valueOf(array.get(6).toString())/mb) + "MB");
		    progressBarTotSwapMdl = Model.of(df.format(Double.valueOf(array.get(7).toString())/mb) +"MB");
		    
		     labelprogressBarVmemory = new Label("labelprogressBarVmemory", progressBarVmemoryMdl);
		     labelprogressBarPsize = new Label("labelprogressBarPsize", progressBarPsizeMdl);
		     labelprogressBarSwapSpace = new Label("labelprogressBarSwapSpace",progressBarSwapSpaceMdl);
		     labelprogressBarCPUload = new Label("labelprogressBarCPUload",progressBarCPUloadMdl);
		     labelprogressBarCPUtime = new Label("labelprogressBarCPUtime",progressBarCPUtimeMdl);
		     labelprogressBarSystemLoad = new Label("labelprogressBarSystemLoad",progressBarSystemLoadMdl);
		     labelprogressBarTotPmemsize = new Label("labelprogressBarTotPmemsize",progressBarTotPmemsizeMdl);
		     labelprogressBarTotSwap = new Label("labelprogressBarTotSwap",progressBarTotSwapMdl);
			
		     labelprogressBarVmemory.setOutputMarkupId(true);
		     labelprogressBarPsize.setOutputMarkupId(true);
		     labelprogressBarSwapSpace.setOutputMarkupId(true);
		     labelprogressBarCPUload.setOutputMarkupId(true);
		     labelprogressBarCPUtime.setOutputMarkupId(true);
		     labelprogressBarSystemLoad.setOutputMarkupId(true);
		     labelprogressBarTotPmemsize.setOutputMarkupId(true); 
		     labelprogressBarTotSwap.setOutputMarkupId(true);

		     
		     
			progressBarVmemory = new ProgressBar("progressBarVmemory");
			progressBarPsize = new ProgressBar("progressBarPsize");
			progressBarCPUload = new ProgressBar("progressBarCPUload");
			progressBarCPUtime = new ProgressBar("progressBarCPUtime");
			progressBarSystemLoad = new ProgressBar("progressBarSystemLoad");
			progressBarTotPmemsize = new ProgressBar("progressBarTotPmemsize");
			progressBarTotSwap = new ProgressBar("progressBarTotSwap");
			progressBarSwapSpace = new ProgressBar("progressBarSwapSpace");
			
			
			progressBarVmemory.setOutputMarkupId(true);
			progressBarPsize.setOutputMarkupId(true);
			progressBarCPUload.setOutputMarkupId(true);
			progressBarCPUtime.setOutputMarkupId(true);
			progressBarSystemLoad.setOutputMarkupId(true);
			progressBarTotPmemsize.setOutputMarkupId(true);
			progressBarTotSwap.setOutputMarkupId(true);
			progressBarSwapSpace.setOutputMarkupId(true);
			try
			{
				progressBarVmemory.setValue((int) Double.parseDouble(array.get(0).toString().substring(0, 2)));
				progressBarPsize.setValue((int) Double.parseDouble(array.get(1).toString().substring(0, 2)));
				progressBarSwapSpace.setValue((int) Double.parseDouble(array.get(2).toString().substring(0, 2)));
				progressBarCPUload.setValue((int)Double.parseDouble(array.get(3).toString().substring(0, 1)));
				progressBarCPUtime.setValue(Integer.parseInt(array.get(4).toString().substring(0, 2)));
				progressBarSystemLoad.setValue((int) Double.parseDouble(array.get(5).toString().substring(0, 1)));
				progressBarTotPmemsize.setValue((int) Double.parseDouble(array.get(6).toString().substring(0, 2)));
				progressBarTotSwap.setValue((int) Double.parseDouble(array.get(7).toString().substring(0, 2)));
			}
	catch (Exception e)

	{
		progressBarVmemory.setValue(1);
		progressBarPsize.setValue(1);
		progressBarSwapSpace.setValue(1);
		progressBarCPUload.setValue(1);
		progressBarCPUtime.setValue(1);
		progressBarSystemLoad.setValue(1);
		progressBarTotPmemsize.setValue(1);
		progressBarTotSwap.setValue(1);
	}
			

			
			final AbstractAjaxTimerBehavior timer = new AbstractAjaxTimerBehavior(
					Duration.seconds(2)) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onTimer(AjaxRequestTarget target) {
					
					
							 ArrayList<Object> array = printUsage();
							  double mb = 1024*1024;
							    int gb = 1024*1024*1024;
							    DecimalFormat df = new DecimalFormat("#0.00");
							   
							    
		
								progressBarVmemory.setOutputMarkupId(true);
								progressBarPsize.setOutputMarkupId(true);
								progressBarCPUload.setOutputMarkupId(true);
								progressBarCPUtime.setOutputMarkupId(true);
								progressBarSystemLoad.setOutputMarkupId(true);
								progressBarTotPmemsize.setOutputMarkupId(true);
								progressBarTotSwap.setOutputMarkupId(true);
								progressBarSwapSpace.setOutputMarkupId(true);		 
							 
								try
								{
									
									
									progressBarVmemory.setValue((int) Double.parseDouble(String.valueOf(array.get(0).toString().substring(0, 2))));
									progressBarPsize.setValue((int) Double.parseDouble(String.valueOf(array.get(1).toString().substring(0, 2))));
									progressBarSwapSpace.setValue((int) Double.parseDouble(String.valueOf(array.get(2).toString().substring(0, 2))));
									progressBarCPUload.setValue((int)Double.parseDouble(String.valueOf(array.get(3).toString().substring(0, 1))));
									progressBarCPUtime.setValue((int) Double.parseDouble(String.valueOf(array.get(4).toString().substring(0, 2))));
									progressBarSystemLoad.setValue((int) Double.parseDouble(String.valueOf(array.get(5).toString().substring(0, 1))));
									progressBarTotPmemsize.setValue((int) Double.parseDouble(String.valueOf(array.get(6).toString().substring(0, 2))));
									progressBarTotSwap.setValue((int) Double.parseDouble(String.valueOf(array.get(7).toString().substring(0, 2))));
								}
						catch (Exception e)

						{
							progressBarVmemory.setValue(1);
							progressBarPsize.setValue(1);
							progressBarSwapSpace.setValue(1);
							progressBarCPUload.setValue(1);
							progressBarCPUtime.setValue(1);
							progressBarSystemLoad.setValue(1);
							progressBarTotPmemsize.setValue(1);
							progressBarTotSwap.setValue(1);
						}
					
					
				    progressBarVmemoryMdl.setObject(df.format(Double.valueOf(array.get(0).toString())/mb) + "MB");
				    progressBarPsizeMdl.setObject(df.format(Double.valueOf(array.get(1).toString())/mb) + "MB");
				    progressBarSwapSpaceMdl.setObject(df.format(Double.valueOf(array.get(2).toString())/mb) + "MB");
				    progressBarCPUloadMdl.setObject(df.format(Double.valueOf(array.get(3).toString())/mb)+ "MB");
				    progressBarCPUtimeMdl.setObject(array.get(4).toString());				     
				    progressBarSystemLoadMdl.setObject(df.format(Double.valueOf(array.get(5).toString())/mb) + "MB");
				     progressBarTotPmemsizeMdl.setObject(df.format(Double.valueOf(array.get(6).toString())/mb) + "MB");
				     progressBarTotSwapMdl.setObject(df.format(Double.valueOf(array.get(7).toString())/mb) + "MB");
				    


				 
				     target.add(form.add(labelprogressBarVmemory));
				     target.add(form.add(labelprogressBarPsize));
				     target.add(form.add(labelprogressBarSwapSpace));
				     target.add(form.add(labelprogressBarCPUload));
				     target.add(form.add(labelprogressBarCPUtime));
				     target.add(form.add(labelprogressBarSystemLoad));
				     target.add(form.add(labelprogressBarTotPmemsize));
				     target.add(form.add(labelprogressBarTotSwap));
					
				}
					

			};
			
			form.add(timer);
			form.add(progressBarVmemory);
			form.add(progressBarPsize);
			form.add(progressBarSwapSpace);
			form.add(progressBarCPUload);
			form.add(progressBarCPUtime);
			form.add(progressBarSystemLoad);
			form.add(progressBarTotPmemsize);
			form.add(progressBarTotSwap);
			form.add( labelprogressBarVmemory);
			form.add( labelprogressBarPsize);
			form.add( labelprogressBarSwapSpace);
			form.add( labelprogressBarCPUload);
			form.add( labelprogressBarCPUtime);
			form.add( labelprogressBarSystemLoad);
			form.add( labelprogressBarTotPmemsize);
			form.add( labelprogressBarTotSwap);
			add(form);
			
			
		}
	private static ArrayList<Object> printUsage() {
		
		 ArrayList<Object> array = new ArrayList<Object>();
		
		  OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		  for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
		    method.setAccessible(true);
		    if (method.getName().startsWith("get") 
		        && Modifier.isPublic(method.getModifiers())) {
		            Object value;
		        try {
		            value = method.invoke(operatingSystemMXBean);
		        } catch (Exception e) {
		            value = e;
		        } // try
		        
		        array.add(value);
		       
		    } // if
		  } // for
		return array;
		}

}
