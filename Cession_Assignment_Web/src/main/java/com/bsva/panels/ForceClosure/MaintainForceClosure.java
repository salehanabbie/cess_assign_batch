package com.bsva.panels.ForceClosure;
import java.io.Serializable;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.TemplatePage;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebSystemParameterModel;
import com.bsva.panels.SystemStatus.LandingPanelSystemStatus;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

public class MaintainForceClosure  extends Panel implements Serializable
{
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainForceClosure.class);
	private TextArea<String> forceClosure ;
	private Button saveOrUpdateButton;
	private Button cancelButton ;
	private Form form ;
	private String action;
	private String inBalanceind="Y";
	public static String id;
	ViewForceClosure viewForceClosure;
	WebSystemParameterModel websystemParametersModel;
	LandingPanelSystemStatus landingPanelSystemStatus;
	private ClientSessionModel clientSessionModel;
	public static Session session;
	
	public MaintainForceClosure(String id)
	{
		super(id);
		this.id=id;
		initializeComponents();
	}

	public MaintainForceClosure(String id,WebSystemParameterModel websystemParametersModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.websystemParametersModel = websystemParametersModel;
    	initializeComponents();
		log.debug("Screen ID:" + action);
	}
	
	public void initializeComponents()
	{
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		form= new Form("MaintainForceClosure");
		add(form);
		
		forceClosure = new TextArea<String>("forceClosureTextField",new Model<String>());
		forceClosure.add(StringValidator.maximumLength(500));
		forceClosure.add(new TextFieldValidator());
		
				saveOrUpdateButton = new Button("saveOrUpdateButton")
				{
					private static final long serialVersionUID = 1L;

						@Override
						public void onSubmit()
						{
							try
							{
									boolean results = false;
									boolean saved = false;
									WebSystemParameterModel websystemParametersModel = null;
									//WebSystemParameterModel websystemParametersModel = new WebSystemParameterModel();
									Date currentDate = new Date();
									short inactiveDuration=1;
										//websystemParametersModel = new WebsystemParametersModel(websystemParametersModel.getDefCurr());
//									websystemParametersModel = new WebSystemParameterModel();
//									websystemParametersModel.setProcessDate(currentDate);
//									websystemParametersModel.setDefCurr("ZAR");
//									websystemParametersModel.setSysName("MANDATES");	
//									websystemParametersModel.setForcecloseReason(forceClosure.getValue());
//									websystemParametersModel.setInactiveDuration(inactiveDuration);
//									websystemParametersModel.setInBalanceInd(inBalanceind);
//									log.info("Web Model: " + websystemParametersModel);
//				
//									SystemParameterModel createSystemParameter = new WebAdminTranslator().translateWebSystemParametersModelToSystemParametersCommonsModel(websystemParametersModel);
//									log.info("CreateSystemParameters in Maintain Force Closure ====>> " + createSystemParameter);
//								
//									results = controller.createSystemParameters(createSystemParameter);
									Controller controller = new Controller();
									results = controller.runEndofDayManually(clientSessionModel.getUsername(), forceClosure.getValue());
									//viewForceClosure = new ViewForceClosure(id);
									log.info("Results --->" + results);
									
									if(results) {
										log.info("Force Closure Successfully Run");
										info("End  of day has ran successfully");
										log.info("End  of day has ran successfully");
									}else {
										landingPanelSystemStatus = new LandingPanelSystemStatus(id);
										info("viewForceClosure Added/Updated...");
										 MarkupContainer markupContainer = form.getParent().getParent();
										 markupContainer.remove(form.getParent());
										//markupContainer.add(viewForceClosure);
										 markupContainer.add(landingPanelSystemStatus);
									//viewForceClosure.setOutputMarkupId(true);
										 landingPanelSystemStatus.setOutputMarkupId(true);
									//viewForceClosure.setOutputMarkupPlaceholderTag(true);
										 landingPanelSystemStatus.setOutputMarkupPlaceholderTag(true);
										//TemplatePage.setContent(viewForceClosure);
										 TemplatePage.setContent(landingPanelSystemStatus);
										//info("Force Closure ran successfully");
										log.info("controller.eodFeedbackMsg :"+controller.eodFeedbackMsg);
										landingPanelSystemStatus.error(controller.eodFeedbackMsg);

									} 

//									MarkupContainer markupContainer = form.getParent().getParent();
//									//markupContainer.add(landingPanelSystemStatus);
//									landingPanelSystemStatus.setOutputMarkupId(true);
//									landingPanelSystemStatus.setOutputMarkupPlaceholderTag(true);
//									TemplatePage.setContent(landingPanelSystemStatus);
//									info("Force Closure ran successfully");
//									else 
//									{
//										error("Reason  could not be added/update...");
//									}
								} 
								catch (Exception exception) 
								{
									error("Add/Update failed, " + exception.getMessage());
									log.error("Add/Update failed, ", exception);
								}
						}
				};
						
						
						cancelButton = new Button ("cancel")
						{
						private static final long serialVersionUID = 1L;
						public void onSubmit ()
						{
//							viewForceClosure = new ViewForceClosure(id);
							landingPanelSystemStatus = new LandingPanelSystemStatus(id);
							MarkupContainer markupContainer = form.getParent().getParent();
							markupContainer.remove(form.getParent());
//							markupContainer.add(viewForceClosure);
							markupContainer.add(landingPanelSystemStatus);
//							viewForceClosure.setOutputMarkupId(true);
							landingPanelSystemStatus.setOutputMarkupId(true);
//							viewForceClosure.setOutputMarkupPlaceholderTag(true);
							landingPanelSystemStatus.setOutputMarkupPlaceholderTag(true);
//							TemplatePage.setContent(viewForceClosure);
							TemplatePage.setContent(landingPanelSystemStatus);
							
//							landingPanelSystemStatus = new LandingPanelSystemStatus(id);
//							landingPanelSystemStatus.setOutputMarkupId(true);
//							landingPanelSystemStatus.setOutputMarkupPlaceholderTag(true);
//							MarkupContainer markupContainer = form.getParent().getParent();
//							markupContainer.remove(form.getParent()	);
//							markupContainer.add(landingPanelSystemStatus);
//							TemplatePage.setContent(landingPanelSystemStatus);
						}
						
					};
					saveOrUpdateButton.setDefaultFormProcessing(false);
					cancelButton.setDefaultFormProcessing(false);
					FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
					add(feedbackPanel);
				        form.add(cancelButton);
						form.add(saveOrUpdateButton);
						form.add(forceClosure);
						//form.add(landingPanelSystemStatus);
		
	}
}
