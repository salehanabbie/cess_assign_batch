package com.bsva.panels.cisDownload;

import java.io.IOException;
import java.io.Serializable;







import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import javax.swing.ProgressMonitor;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.time.Duration;
import org.odlabs.wiquery.core.options.Options;
import org.odlabs.wiquery.ui.progressbar.ProgressBar;

import com.bsva.ProgressBar.Progression;
import com.bsva.commons.model.AudSystemProcessModel;
import com.bsva.commons.model.OpsProcessControlCommonsModel;
import com.bsva.commons.model.OpsProcessControlModel;
import com.bsva.commons.model.SysCtrlSlaTimeModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.ProgressBarModel;
import com.bsva.validators.SimpleAttributeModifier;

public class CISDownloadPanel extends Panel implements Serializable {

	public static Logger log = Logger.getLogger(CISDownloadPanel.class);
	private static final long serialVersionUID = 1L;
	private int width = 400;
	
	//private static final ResourceReference CSS = new PackageResourceReference(CISDownloadPanel.class ,"ProgressBar.css");
	private Form form;
	private Button cisDownloadButton;
	Controller controller;
	public static String id;
	Date currentDate = new Date(); 
/*	private ProgressBar progressBar;
    private Label progressBarPercentage;
    private Model<String> percentage;*/
	
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	
    
	public CISDownloadPanel(String id)
	{	
		super(id);
		this.id=id;
		initializaComponents();	
	}


	private void initializaComponents() 
	{	
		session = getSession();
        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
        
        userName = clientSessionModel.getUsername();
		
	/*	//25%
		percentage = Model.of("0%");
		progressBar = new ProgressBar("progressBar");
        progressBar.setValue(Integer.valueOf("0"));
        progressBarPercentage = new Label("progressBarPercentage", percentage);*/
         
		controller=new Controller();
		form=new Form ("form");
		
		Runtime instance = Runtime.getRuntime();
		
		int mb =1024*1024;
		long total = instance.totalMemory()/mb;
		long used =total-instance.freeMemory()/mb;
	
		add(form);
		
		
		/*progressBar= new ProgressBar("progressBar");
		add(progressBar);*/	
		
		cisDownloadButton =new Button ("cisDownloadButton")
		{
        private static final long serialVersionUID = 1L;
			
			public void onSubmit() 
			{	 
				log.info("[<===== "+userName+" REQUESTED CIS DOWNLOAD=====>]");
				SysCtrlSlaTimeModel sysCtrlSlaTimeModel   = (SysCtrlSlaTimeModel)controller.retrieveCisSlaTime("CIS");
//				log.info("sysCtrlSlaTimeModel ===> "+sysCtrlSlaTimeModel);
				
				log.info("Service: "+sysCtrlSlaTimeModel.getService()+" - "+"SLA Start Time: "+sysCtrlSlaTimeModel.getStartTime()+" - "+"SLA End Time: "+sysCtrlSlaTimeModel.getEndTime());
			
				 SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
					
			        Calendar cal = Calendar.getInstance();
					 cal.setTime(currentDate);
				
					 //Put it back in the Date object
					 currentDate = cal.getTime();
			        
			        String strrDate = parser.format(currentDate);
			        log.info("Time: "+strrDate);
			        
			        Date userDate = null;
					try {
						userDate = parser.parse(strrDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Date startTime = null;
					try {
						startTime = parser.parse(sysCtrlSlaTimeModel.getStartTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Date endTime = null;
					try {
						endTime = parser.parse(sysCtrlSlaTimeModel.getEndTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(userDate.after(startTime) && userDate.before(endTime))
					{
						OpsProcessControlModel opsProcessControlModel = (OpsProcessControlModel) controller.retrieveCisDownloadDate();
						log.info("MDT_AC_OPS_PROCESS_CONTROL DATA ===> "+opsProcessControlModel);

						if(opsProcessControlModel == null)
						{
							//Update System Audit Log Information
							AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
							audSystemProcessModel.setProcess(controller.getProperty("AUD.SYSPROCESS.CIS"));
							audSystemProcessModel.setProcessDate(new Date());
							audSystemProcessModel.setUserId(userName);
							
							controller.saveSystemAuditInfo(audSystemProcessModel);
							
							controller.runCisDownload();
							info("CIS Download completed successfully");
							log.info("<===== CIS Download completed successfully =====>");
						}
						else
						{
							if(opsProcessControlModel.getCisDownloadInd() != null && opsProcessControlModel.getCisDownloadInd().equalsIgnoreCase("Y"))
							{
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								String procDate = sdf.format(opsProcessControlModel.getProcessDate());
								error("CIS Download already exists for processing date: "+procDate);
								log.error("CIS Download already exists for processing date: "+procDate);
							}
							else
							{
								error("An error occured on CIS Download!");
								log.error("An error occured on CIS Download!");
							}
						}
					}

					else{
						log.error("SLA TIME for CIS is not reached, Please run from "+sysCtrlSlaTimeModel.getStartTime()+"!");
						error("SLA TIME for CIS is not reached, Please run from "+sysCtrlSlaTimeModel.getStartTime()+"!");
						

					}
			}

			};

		

			 /*percentage.setObject("100 %");
		 progressBar.setValue(Integer.valueOf("100"));
		 progressBarPercentage = new Label("progressBarPercentage", percentage);
		 progressBarPercentage.setOutputMarkupId(true);
		 progressBar.setOutputMarkupId(true);*/
		
//	progressBar.setVisible(false);
//    form.add(progressBarPercentage);
  //  form.add(progressBar);
  	form.add(cisDownloadButton);
  	cisDownloadButton.add(new SimpleAttributeModifier("onclick", "Are you sure you want to run CIS Download?"));
  	
  	form.add(new FeedbackPanel("feedbackPanel"));
	
		}
		
	
	
	/*@Override
		public void renderHead(IHeaderResponse response)
	{
			super.renderHead(response);
	
		//	response.renderCSSReference(CSS);
		}
	
	
	@Override
		protected void onInitialize()
		{
			super.onInitialize();
	
			if (getParent() != null)
			{
				
				 * Add the css to our parent incase the bar is not initially visible.
				 
				getParent().add(new Behavior()
				{

					
	 
					private static final long serialVersionUID = 1L;
	
					@Override
					public void renderHead(Component component, IHeaderResponse response)
					{
						super.renderHead(component, response);
						//response.renderCSSReference(CSS);
					}
	
	
				});
			}
		}
		
	public ProgressBar(String id, ProgressBarModel model)
		{
			super(id, model);
	
			// add CSS to parent to render the CSS even if the progress bar is initially
			// invisible
	
	
			add(new Label("label", getLabelModel(model)));
			add(new Label("message", getMessageModel(model)));
			add(new WebMarkupContainer("bar").add(new AttributeModifier("style",
				new AbstractReadOnlyModel<String>()
				{
					
	 
				private static final long serialVersionUID = 1L;
	
					@Override
					public String getObject()
					{
						ProgressBar model = (ProgressBar)getDefaultModel();
						Progression progression = model.getProgression();
	
						// set the width of the bar in % of the progress
						// this is coupled with the specific CSS
						return "width: " + progression.getProgress() + "%";
					}
				})));
			setOutputMarkupId(true);
		}

		
    form.add(new IndicatingAjaxButton("submit", form) {
        protected void onSubmit(AjaxRequestTarget target, Form form) {
            bar.start(target);
            new Thread() {
                public void run() {
                    for(int i = 0; i <= 100; i++) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) { }
                            progress = i;
                        }
                }
            }.start();
        }
    });
    
    
	protected void onFinished(AjaxRequestTarget target)
	{
		  setVisible(false);
          target.appendJavaScript("alert('Task done!')");
	}
	
		protected AbstractReadOnlyModel<String> getLabelModel(final Progression model)
		{
			return new AbstractReadOnlyModel<String>()
			{
				
	 
				private static final long serialVersionUID = 1L;
	
			@Override
				public String getObject()
				{
					ProgressBarModel progression = model.getProgression();
					return progression.getProgress() + "%";
				}
		};
		}

		
	
	
		protected IModel<String> getMessageModel(final Progression model)
		{
			return new AbstractReadOnlyModel<String>()
			{
				
	 
				private static final long serialVersionUID = 1L;
	
				@Override
				public String getObject()
				{
					return model.getProgression().getProgressMessage();
				}
			};
		}

		
	
		public void start(AjaxRequestTarget target)
		{
			setVisible(true);
			add(new AjaxSelfUpdatingTimerBehavior(Duration.ONE_SECOND)
			{
				
	 
				private static final long serialVersionUID = 1L;
	
			@Override
				protected void onPostProcessTarget(AjaxRequestTarget target)
				{
				Progression model = (Progression)getDefaultModel();
				ProgressBarModel progression = model.getProgression();
					if (progression.isDone())
					{
						// stop the self update
						stop(target);
						// do custom action
						onFinished(target);
					}
				}
			});
			if (getParent() != null)
			{
				target.add(getParent());
			}
			else
			{
			target.add(this);
			}
		}

		


		
	
		public int getWidth()
		{
			return width;
		}

		
	
		public void setWidth(int width)
		{
			this.width = width;
	}*/
	
}
