package com.bsva.panels.RejectReasonCodes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.TemplatePage;
import com.bsva.commons.model.CnfgRejectReasonCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebRejectReasonCodesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.ReasonCodesProvider;
import com.bsva.provider.RejectReasonProvider;
import com.bsva.validators.TextFieldValidator;

public class ViewRejectReasoCodesPanel   extends Panel implements Serializable 
{
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	public static String id;
	public static Logger log = Logger.getLogger(ViewRejectReasoCodesPanel.class);	
	private Form form;
	private Set<WebRejectReasonCodesModel> selected = new HashSet<WebRejectReasonCodesModel>();
	private MaintainRejectReasonCodesPanel  maintainRejectReasoncodes;
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	String action;
	private TextField<String> searchText;
	private List<WebRejectReasonCodesModel> webRejectReasonCodesModelList;
	private List<CnfgRejectReasonCodesModel> rejectReasonCodesList;
	Controller controller;
	private ClientSessionModel clientSessionModel;
    public static Session session;
    private String userRole;
    
	public ViewRejectReasoCodesPanel(String id) 
	{
		super(id);
		initializeComponents();
		this.id=id;
	}

	private void initializeComponents ()
	{
		controller = new Controller();
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		userRole = clientSessionModel.getUserRole();
		form = new Form("viewRejectReasonCodesPanel");
		form.add(createDataTable(new RejectReasonProvider()));

		updateButton = new Button("updateButton") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				if (selected.size() > 1) 
				{
					error("Please select only one record...");
				}
				else if (selected.size() <= 0) 
				{
					error("No record was selected...");
				} 
				else 
				{
					for (WebRejectReasonCodesModel  rejectReasonModel : selected)
					{
						maintainRejectReasoncodes = new MaintainRejectReasonCodesPanel(id,rejectReasonModel,"update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainRejectReasoncodes);
						maintainRejectReasoncodes.setOutputMarkupId(true);
						maintainRejectReasoncodes.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainRejectReasoncodes);
					}
				}
			}
		};
		updateButton.setDefaultFormProcessing(false);
		if(userRole.equalsIgnoreCase(controller.getProperty("MDT.OPS")))
        {
            updateButton.setEnabled(false);
        }

		addButton = new Button("addButton") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				maintainRejectReasoncodes = new MaintainRejectReasonCodesPanel(id,"create");
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainRejectReasoncodes);
				maintainRejectReasoncodes.setOutputMarkupId(true);
				maintainRejectReasoncodes.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainRejectReasoncodes);
			}
		};
		addButton.setDefaultFormProcessing(true);
		if(userRole.equalsIgnoreCase(controller.getProperty("MDT.OPS")))
        {
            addButton.setEnabled(false);
        }

		searchButton = new Button("searchButton") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				viewAllButton.setEnabled(true);
				log.debug("Search Text: " + searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					error("Search Field is not populated.");
				}
				else
				{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new RejectReasonProvider(searchText	.getValue().toUpperCase())));
				}
			}
		};
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(4));
     	searchText.add(new TextFieldValidator());
     	
    	viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new RejectReasonProvider()));
			}
		};
		
		form.add(updateButton);
		form.add(addButton);
		form.add(searchButton);
		form.add(searchText);
		form.add(viewAllButton);
		viewAllButton.setEnabled(false);
		add(new FeedbackPanel("feedbackPanel"));
		add(form);
	}

	private DefaultDataTable createDataTable(	RejectReasonProvider rejectReasonProvider)
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebRejectReasonCodesModel>(Model.of("Select")) 
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected IModel newCheckBoxModel(final IModel<WebRejectReasonCodesModel> rowModel)
			{
				return new AbstractCheckBoxModel() 
				{
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isSelected() 
					{
						return selected.contains(rowModel.getObject());
					}

					@Override
					public void select() 
					{
						if (selected.size() > 0) 
						{
							unselect();
						}
						selected.add(rowModel.getObject());
					}

					@Override
					public void unselect() 
					{
						selected.remove(rowModel.getObject());
					}

					@Override
					public void detach() 
					{
						rowModel.detach();
					}
				};
			}
		};
		columns.add(column);
		columns.add(new PropertyColumn(new Model<String>("Reject Reason Code"),"rejectReasonCode", "rejectReasonCode"));
		columns.add(new PropertyColumn(new Model<String>("Reject Reason Code Description"),"rejectReasonDesc","rejectReasonDesc"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),	"activeInd", "activeInd"));
		dataTable = new DefaultDataTable("dataTable", columns,	rejectReasonProvider, 10);
		return dataTable;
	}		
}