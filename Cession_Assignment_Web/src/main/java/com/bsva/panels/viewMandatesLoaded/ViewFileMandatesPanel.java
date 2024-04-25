package com.bsva.panels.viewMandatesLoaded;

import com.bsva.TemplatePage;
import com.bsva.controller.Controller;
import com.bsva.models.WebMandateModel;
import com.bsva.models.WebOpsAccptMsgDetailModel;
import com.bsva.panels.systemInfo.DelDeliveryPanel;
import com.bsva.provider.ViewFileMandatesProvider;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * 
 * @author DimakatsoN
 *
 */
public class ViewFileMandatesPanel extends Panel implements Serializable {
	

	public static String id;

	public Form form;

	Controller controller;

	private Button viewButton;
	
	private Button cancelButton;
	
	private String action;
	
	//private DefaultDataTable grpHdrAccptTable, mandateAccptTable;
	
	private ViewFileMandatesProvider viewFileMandatesProvider;

	private List<WebMandateModel> mandatesList;
	DelDeliveryPanel deldeliverypanel;

	private Set<WebMandateModel> selected = new HashSet<WebMandateModel>();
	
	//List<WebViewFileStatusModel> grpHdrAccptList;
	//List<WebViewFileStatusModel> mndtAccptList;
	List<WebOpsAccptMsgDetailModel> grpHdrAccptList;
	List<WebOpsAccptMsgDetailModel> mndtAccptList ;
	
	
	
/*	public ViewFileMandatesPanel(String id, List<WebOpsAccptMsgDetailModel> webAccptGrpHdrList, List<WebOpsAccptMsgDetailModel> webAccptMandateList, String string) {
		super(id);
		this.id=id;
		//initializeComponents();
	}*/

	/*public ViewFileMandatesPanel(String id, String action, List<WebMandateModel> mandatesList)*/
	public ViewFileMandatesPanel(String id, String action, List<WebOpsAccptMsgDetailModel> grpHdrAccptList,List<WebOpsAccptMsgDetailModel> mndtAccptList)
	{
		super(id);
		this.id = id;
		this.action = action;
		this.grpHdrAccptList = grpHdrAccptList;
		this.mndtAccptList = mndtAccptList;
		initializeComponents();

	}


	


	private void initializeComponents() 
	{
		controller = new Controller();
		form = new Form("viewFileMandates");
		
		//form.add(createDataTable(new ViewFileMandatesProvider(mandatesList)));*/
		
		System.out.println("grpHdrAccptList====***** View File***"+grpHdrAccptList);
		System.out.println("mndtAccptList====***** View File***"+mndtAccptList);
		
//		form.add(createAccptGrpHdrTable(new AcceptGrpHdrProvider(grpHdrAccptList)));
//		form.add(createAccptMdtTable(new AcceptMandateProvider(mndtAccptList)));
		

		/*viewButton = new Button("viewButton") {
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
					for (WebMandateModel webMandateModel : selected) 
					{
						captureMandatePanel = new CaptureMandatePanel(id,webMandateModel, "deldelivery");
						MarkupContainer markupContainer = form.getParent().getParent();

						markupContainer.remove(form.getParent());
						markupContainer.add(captureMandatePanel);
						break;
					}
				}
			}
			
		};
		viewButton.setDefaultFormProcessing(false);
		viewButton.setVisible(true);*/
		
		
		cancelButton = new Button("cancelButton") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
                deldeliverypanel=new DelDeliveryPanel(id);
                MarkupContainer markupContainer = form.getParent().getParent();
                markupContainer.remove(form.getParent());
                markupContainer.add(deldeliverypanel);
                deldeliverypanel.setOutputMarkupId(true);
                deldeliverypanel.setOutputMarkupPlaceholderTag(true);
                TemplatePage.setContent(deldeliverypanel);

			
			
			}
		};
		cancelButton.setDefaultFormProcessing(false);
		form.add(cancelButton);
		/*form.add(viewButton);*/
		add(form);
		add(new FeedbackPanel("feedback"));


	}

//	private DefaultDataTable createAccptGrpHdrTable(AcceptGrpHdrProvider acceptGrpHdrProvider)
//	{
//		DefaultDataTable grpHdrTable;
//		List<IColumn> columns = new ArrayList<IColumn>();
//
//
//		columns.add(new PropertyColumn(new Model<String>("File Name"),"fileName", "fileName"));
//		columns.add(new PropertyColumn(new Model<String>("Original Message Id"),"origMessageId", "origMessageId"));
//		//columns.add(new PropertyColumn(new Model<String>("Mandate Requested Id"),"manReqId", "manReqId"));
//		columns.add(new PropertyColumn(new Model<String>("Status"),"accepted", "accepted"));
//		columns.add(new PropertyColumn(new Model<String>("Reject Reason Code"), "rejectReasonCode",	"rejectReasonCode"));
//		columns.add(new PropertyColumn(new Model<String>("Reject Reason"),"addRejectRsnInf", "addRejectRsnInf"));
//		grpHdrTable = new DefaultDataTable("grpHdrTable", columns, acceptGrpHdrProvider, 10);
//
//		return grpHdrTable;
//	}
	
	
//	private DefaultDataTable createAccptMdtTable(AcceptMandateProvider acceptMandateProvider)
//	{
//		DefaultDataTable mndtTable;
//		List<IColumn> columns = new ArrayList<IColumn>();
//		columns.add(new PropertyColumn(new Model<String>("File Name"),"fileName", "fileName"));
//		columns.add(new PropertyColumn(new Model<String>("Mandate Download Requested  Id"),"manDwnReqId", "manDwnReqId"));
//		//columns.add(new PropertyColumn(new Model<String>("Original Mandate Id"),"origMandateId", "origMandateId"));
//		columns.add(new PropertyColumn(new Model<String>("Accepted Indicator"),"accepted", "accepted"));
//		columns.add(new PropertyColumn(new Model<String>("Reject Reason Code"), "rejectReasonCode",	"rejectReasonCode"));
//		columns.add(new PropertyColumn(new Model<String>("Reject Reason"),"addRejectRsnInf", "addRejectRsnInf"));
//
//		mndtTable = new DefaultDataTable("mndtTable", columns, acceptMandateProvider, 10);
//
//		return mndtTable;
//	}
	
	

	//________________________REDUNDANT CODE- TO REMOVE ONCE TESTED_________________________________//
/*	private DefaultDataTable createDataTable(ViewFileMandatesProvider viewFileMandatesProvider) {
		DefaultDataTable dataTable;

		List<IColumn> columns = new ArrayList<IColumn>();

		IColumn column = new CheckBoxColumn<WebMandateModel>(Model.of("Select")) {

			@Override
			protected IModel newCheckBoxModel(
					final IModel<WebMandateModel> rowModel) {
				return new AbstractCheckBoxModel() {

					@Override
					public boolean isSelected() {
						return selected.contains(rowModel.getObject());
					}

					@Override
					public void select() {
						if (selected.size() > 0) {
							unselect();
						}
						selected.add(rowModel.getObject());
					}

					@Override
					public void unselect() {
						selected.remove(rowModel.getObject());
					}

					@Override
					public void detach() {
						rowModel.detach();
					}
				};
			}
		};

		columns.add(column);

		columns.add(new PropertyColumn(new Model<String>("Mandate Id"),"mandateId", "mandateId"));
		columns.add(new PropertyColumn(new Model<String>("Debtor Name"),"debtorName", "debtorName"));
		columns.add(new PropertyColumn(new Model<String>("Creditor Name"),"creditorName", "creditorName"));
		columns.add(new PropertyColumn(new Model<String>("From Date"),"fromDate", "fromDate"));
		columns.add(new PropertyColumn(new Model<String>("To Date"), "toDate",	"toDate"));
		columns.add(new PropertyColumn(new Model<String>("Collection Amount"),"collectionAmt", "collectionAmt"));
		columns.add(new PropertyColumn(new Model<String>("Status"), "processStatus","processStatus"));

		dataTable = new DefaultDataTable("dataTable", columns,viewFileMandatesProvider, 5);

		return dataTable;
	}*/

//
//	public List<WebMandateModel> retrieveCompleteMandate(List<WebMandateModel> localList)
//	{
//		List<WebMandateModel> tempMandatesList = null;
//		if(localList.size() > 0)
//		{
//
//			tempMandatesList = new ArrayList<WebMandateModel>();
//
//			for (WebMandateModel localMandatesModel : localList)
//			{
//					String mandateReqId = localMandatesModel.getMandateReqId();
//
//					String bankId = mandateReqId.substring(0, 8);
//					String credId = mandateReqId.substring(8, 16);
//					String contractNum = mandateReqId.substring(16);
//
//					System.out.println("mandateReqId===>  "+ mandateReqId);
//					System.out.println("bankId===>  " + bankId);
//					System.out.println("credId===>  " + credId);
//					System.out.println("contractNum===>  "+ contractNum);
//
//					localMandatesModel.setContractNo(contractNum);
//					localMandatesModel.setCreditorId(credId);
//
//					/**********************Retrieve Party Identification Info **********************/
//					List<PartyIdentModel> localPartyIdentList = retrievePartyIdent(mandateReqId);
//
//					if(localPartyIdentList.size() > 0)
//					{
//						for (PartyIdentModel localModel : localPartyIdentList)
//						{
//							System.out.println("partyId: "+localModel.getPartyIdentTypeId());
//							if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI06"))
//								localMandatesModel.setCreditorSchemeName(localModel.getName());
//
//								if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI02"))
//								{
//									localMandatesModel.setCreditorName(localModel.getContactName());
//									localMandatesModel.setCreditorContactDetail(localModel.getPhoneNr());
//								}
//
//								if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI03"))
//								{
//									localMandatesModel.setUltimateCreditorName(localModel.getContactName());
//									localMandatesModel.setUltimateCreditorContactDetail(localModel.getPhoneNr());
//								}
//
//								if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI04"))
//								{
//									localMandatesModel.setDebtorName(localModel.getContactName());
//									localMandatesModel.setDebtorContactDetail(localModel.getPhoneNr());
//									localMandatesModel.setDebtorIdNo(localModel.getId());
//								}
//
//								if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI05"))
//								{
//									localMandatesModel.setUltimateDebtorName(localModel.getContactName());
//									localMandatesModel.setUltimateDebtorContactDetail(localModel.getPhoneNr());
//								}
//						}//end of for(PartyIdentList)
//					}//end of if(size > 0)
//
//
//
//					/**********************Retrieve Party Identification Info **********************/
//					List<CashAccountModel> localCashAccountList = retrieveCashAccount(mandateReqId);
//
//					if(localCashAccountList.size() > 0)
//					{
//						for (CashAccountModel localModel : localCashAccountList)
//						{
//							if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI02"))
//								localMandatesModel.setCreditorAccNo(localModel.getAccountNumber());
//
//							if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI04"))
//							{
//								localMandatesModel.setDebtorAccNo(localModel.getAccountNumber());
//								localMandatesModel.setDebtorAccName(localModel.getAccountName());
//							}
//						}//end of for(CashAccountList)
//					}//end of if (size >0 )
//
//
//
//					/**********************Retrieve Party Identification Info **********************/
//					List<FinInstModel> localFinInstList = retrieveFinInstitution(mandateReqId);
//
//					if(localFinInstList.size() > 0)
//					{
//						for (FinInstModel localModel : localFinInstList)
//						{
//							if(localModel.getFinInstTypeId().equalsIgnoreCase("FI04"))
//							{
//								localMandatesModel.setDebtorBank(localModel.getFiName());
//								localMandatesModel.setDebtorBranchNo(localModel.getBranchId());
//							}
//
//							if(localModel.getFinInstTypeId().equalsIgnoreCase("FI03"))
//							{
//								localMandatesModel.setCreditorBank(localModel.getFiName());
//								localMandatesModel.setCreditorBranchNo(localModel.getBranchId());
//							}
//						}//end of for(FinAccList)
//					}//end of if (size >0 )
//
//
//					System.out.println("localMandatesModel=================>"+ localMandatesModel.toString());
//					tempMandatesList.add(localMandatesModel);
//			}//end of loop through list
//		}//end of size > 0
//
//		return tempMandatesList;
//	}//end of retrieveComplete...()
	
	
	
	

}
