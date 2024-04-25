
package com.bsva.panels.systemInfo;

import com.bsva.commons.model.MandatesModel;
import com.bsva.commons.model.MdtSysCtrlSysParamModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.controller.Controller;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.models.WebMandateModel;
import com.bsva.models.WebOpsAccptMsgDetailModel;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.panels.viewMandatesLoaded.ViewFileMandatesPanel;
import com.bsva.provider.DelDeliveryProvider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
/**
 * 
 * @author DimakatsoN
 *
 */



public class DelDeliveryPanel  extends Panel implements Serializable{

	private static AdminBeanRemote adminBeanRemote;
	public static String id;
	
	private Form<?> form;
	
	Controller controller;
	
	private Set<WebOpsFileRegModel> selected  = new HashSet<WebOpsFileRegModel>();
	
	private  List<WebOpsFileRegModel> webOpsFileReglList;
	
	private  List<OpsFileRegModel> opsFileRegList;
	DelDeliveryPanel deldeliverypanel;
	
	private DefaultDataTable dataTable;
	private DelDeliveryProvider delDeliveryProvider;
	
	private Button viewMandateButton;
	private Button deleteFileButton;

	List<WebMandateModel> webMandateList = new ArrayList<WebMandateModel>();
	WebMandateModel webMandateModel = new WebMandateModel();
	
	List<WebOpsAccptMsgDetailModel> webAccptGrpHdrList = new ArrayList<WebOpsAccptMsgDetailModel>();
	List<WebOpsAccptMsgDetailModel> webAccptMandateList = new ArrayList<WebOpsAccptMsgDetailModel>();
	
	
	private List<WebMandateModel> webMandatesList;
	private List<WebMandateModel> tempMandatesList;
	
	private List<IColumn> columns;
	
	ViewFileMandatesPanel viewFileMandatesPanel;
	MdtSysCtrlSysParamModel mdtSysCtrlSysParamModel;
	String systemType;
	private String sadcSystem = "SADC";
	
	public DelDeliveryPanel(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents();

	}


	private void initializeComponents() 
	{
		columns = new  ArrayList<IColumn>();
		
		form = new Form("form");

		controller = new Controller();

		//Retrieve System Paramaters
		mdtSysCtrlSysParamModel = new MdtSysCtrlSysParamModel();
		mdtSysCtrlSysParamModel = (MdtSysCtrlSysParamModel) controller.retrieveActiveSystemParameters();
		
		if(mdtSysCtrlSysParamModel != null)
			systemType = mdtSysCtrlSysParamModel.getSysType();
		
		form.add(createDataTable(new DelDeliveryProvider()));
		
		
		viewMandateButton = new Button("viewMandateButton")
		{
		
		/**
			 * 
			 */
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
				
				for (WebOpsFileRegModel localModel : selected) 
				{
					String grpHdrMsgId = localModel.getGrpHdrMsgId();
					String fileName = localModel.getFileName();
			   
					
					
					
					if(localModel.getStatus().equalsIgnoreCase("FS") || localModel.getStatus().equalsIgnoreCase("FG") || localModel.getStatus().equalsIgnoreCase("LE"))
					{
						
						if(systemType.equalsIgnoreCase(sadcSystem))
						{
//							generateSADCFileView(grpHdrMsgId, fileName);
						}
						else
						{
							
						}
						
					}
					else
						info("File has no errors");
					
					
					
				}
			}
		}
	};
	
	viewMandateButton.setDefaultFormProcessing(false);
	viewMandateButton.setVisible(true);
	//dataTable = createDataTable(new DelDeliveryProvider());
	//dataTable.setOutputMarkupId(true);

	deleteFileButton = new Button("deleteFileButton")
	{
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
			
					for (WebOpsFileRegModel localModel : selected) {
						String status = localModel.getStatus();
				
						
						List<OpsFileRegModel> opsFileRegList = new ArrayList<OpsFileRegModel>();
						opsFileRegList = (List<OpsFileRegModel>) controller.retrieveOpsFileRegByCriteria("MdtOpsFileRegEntity.findByStatus","status", status);
						if (opsFileRegList.size() > 0) {
							for (OpsFileRegModel opsFileRegModel : opsFileRegList) {

								if (opsFileRegModel.getStatus()	.equalsIgnoreCase("FS")) {
									controller	.deleteFileStatus(opsFileRegModel);
								} else if (opsFileRegModel.getStatus().equalsIgnoreCase("FG")) {
									controller	.deleteFileStatus(opsFileRegModel);
								} else {
									error("Invalid status for delete");
								}
							}
						}

					}
				}
			}
		};
	deleteFileButton.setDefaultFormProcessing(false);
	deleteFileButton.setVisible(true);

	/*final AbstractAjaxTimerBehavior timer = new AbstractAjaxTimerBehavior(Duration.seconds(30)) 
	{
		private static final long serialVersionUID = 1L;

				@Override
				protected void onTimer(AjaxRequestTarget target) {
					delDeliveryProvider = new DelDeliveryProvider();
					DefaultDataTable defaultDataTable = createDataTable(new DelDeliveryProvider());
		          	dataTable.replaceWith(defaultDataTable);
					dataTable = defaultDataTable;
					defaultDataTable.setOutputMarkupId(true);
					defaultDataTable.setOutputMarkupPlaceholderTag(true);
					target.add(dataTable);
		
				}
	};
	
	
	form.add(timer);*/
	form.add(viewMandateButton);
	form.add(deleteFileButton);
	add(form);
	add(new FeedbackPanel("feedback"));


}

		
//		public void generateACFileView(String grpHdrMsgId, String fileName)
//		{
//			List<OpsStatusHdrsModel> opsStatusHdrsModelList = new ArrayList<OpsStatusHdrsModel>();
//			List<OpsStatusDetailsModel> groupHeaderErrorList = new ArrayList<OpsStatusDetailsModel>();
//			List<OpsStatusDetailsModel> mandatesErrorList = new ArrayList<OpsStatusDetailsModel>();
//			
//			OpsStatusHdrsModel localWebModel;
//			System.out.println("grpHdrMsgId: "+grpHdrMsgId + "       fileName: "+fileName);
//			
//			opsAccptMsgDetailList = (List<OpsAccptMsgDetailModel>) controller.retrieveMsgDetailByCriteria("MdtOpsAccptMsgDetailEntity.findByOrigMessageId", "origMessageId",grpHdrMsgId);
//			
//			if(opsAccptMsgDetailList.size() > 0)
//			{
//				for (OpsAccptMsgDetailModel opsAccptMsgDetailModel : opsAccptMsgDetailList) 
//				{
//					localWebModel = new WebOpsAccptMsgDetailModel();
//					
//					if(opsAccptMsgDetailModel.getRecordType().equalsIgnoreCase("HDR"))
//					{
//						localWebModel = new WebAdminTranslator().translateCommonsOpsAccptMsgDetailModelToWebModel(opsAccptMsgDetailModel);
//						localWebModel.setFileName(fileName);
//						webAccptGrpHdrList.add(localWebModel);
//					}
//						
//					
//					
//					if(opsAccptMsgDetailModel.getRecordType().equalsIgnoreCase("MDT"))
//					{
//						localWebModel = new WebAdminTranslator().translateCommonsOpsAccptMsgDetailModelToWebModel(opsAccptMsgDetailModel);
//						localWebModel.setFileName(fileName);
//						webAccptMandateList.add(localWebModel);
//					}
//					
//					
//					if(webAccptGrpHdrList.size() > 0 || webAccptMandateList.size() > 0)
//					{
//						 viewFileMandatesPanel = new ViewFileMandatesPanel(id,"VIEWMANDATE", webAccptGrpHdrList,webAccptMandateList);
//						   
//						 MarkupContainer markupContainer = form.getParent().getParent();
//					
//						if (markupContainer == null)
//						{
//							markupContainer=form.getParent();
//							
//					
//						}
//			
//						 markupContainer.remove(form.getParent());
//				
//						 markupContainer.add(viewFileMandatesPanel);
//			
//						 viewFileMandatesPanel.setOutputMarkupId(true);
//						 viewFileMandatesPanel.setOutputMarkupPlaceholderTag(true);
//						 TemplatePage.setContent(viewFileMandatesPanel);
//					}
//					else
//					{
//						info("No errors logged on error table for "+fileName);
//					}
//					
//				   	
//				
//				}
//			}
//		}
//
//		
		private DefaultDataTable createDataTable(DelDeliveryProvider delDeliveyProvider)

		{
			DefaultDataTable dafaultTable;
			
			List<IColumn> columns = new ArrayList<IColumn>();
			
			IColumn column = new CheckBoxColumn<WebOpsFileRegModel>(Model.of("Select")) 
			{
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
	            protected IModel newCheckBoxModel(final IModel<WebOpsFileRegModel> rowModel) 
	            {
	               return new AbstractCheckBoxModel() 
	               {

	                    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

						@Override
	                    public boolean isSelected() 
	                    {
	                       return selected.contains(rowModel.getObject());
	                    }

	                    @Override
	                    public void select() 
	                    {
	                    	if(selected.size() > 0)
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
			columns.add(new PropertyColumn(new Model<String>("FileName"),"fileName", "fileName"));
			columns.add(new PropertyColumn(new Model<String>("Status"),"status","status"));
			//columns.add(new PropertyColumn(new Model<String>("Reason"),"reason", "reason"));
			columns.add(new PropertyColumn(new Model<String>("ProcessDate"),"processDate","processDate"));
			columns.add(new PropertyColumn(new Model<String>("Grp Hdr Msg ID"),"grpHdrMsgId","grpHdrMsgId"));
     		dataTable = new DefaultDataTable("dataTable", columns, delDeliveyProvider, 100);
			
			return dataTable;
			
		}
		
		
//		public List<MandatesModel> retrieveMandate(String grpHdrMsgId)
//		{
//			List<MandatesModel> mandateList = new ArrayList<MandatesModel>();
//			mandateList = (List<MandatesModel>) controller.retrieveMandateByCriteria("MdtOpsMandateRegisterEntity.findByMsgId", "msgId", grpHdrMsgId);
//
//			return mandateList;
//		}
		
//		public List<OpsFileRegModel> retrieveOpsFileReg(String grpHdrMsgId)
//		{
//			List<OpsFileRegModel> opsFileRegList = new ArrayList<OpsFileRegModel>();
//			opsFileRegList = (List<OpsFileRegModel>)controller.retrieveMsgDetailByCriteria("MdtOpsAccptMsgDetailEntity.findByOrigMessageId", "origMessageId",grpHdrMsgId);
//			System.out.println("grpHdrMsgId List===>"+opsFileRegList.toString());
//			return opsFileRegList;
//
//		}
		

//
//		public List<WebMandateModel> retrieveCompleteMandate(List<WebMandateModel> localList)
//		{
//			List<WebMandateModel> tempMandatesList = null;
//			if(localList.size() > 0)
//			{
//
//				tempMandatesList = new ArrayList<WebMandateModel>();
//
//				for (WebMandateModel localMandatesModel : localList)
//				{
//						String mandateReqId = localMandatesModel.getMandateReqId();
//
//						String bankId = mandateReqId.substring(0, 8);
//						String credId = mandateReqId.substring(8, 16);
//						String contractNum = mandateReqId.substring(16);
//
//						System.out.println("mandateReqId===>  "+ mandateReqId);
//						System.out.println("bankId===>  " + bankId);
//						System.out.println("credId===>  " + credId);
//						System.out.println("contractNum===>  "+ contractNum);
//
//						localMandatesModel.setContractNo(contractNum);
//						localMandatesModel.setCreditorId(credId);
//
//						/**********************Retrieve Party Identification Info **********************/
//						List<PartyIdentModel> localPartyIdentList = retrievePartyIdent(mandateReqId);
//
//						if(localPartyIdentList.size() > 0)
//						{
//							for (PartyIdentModel localModel : localPartyIdentList)
//							{
//								System.out.println("partyId: "+localModel.getPartyIdentTypeId());
//								if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI06"))
//									localMandatesModel.setCreditorSchemeName(localModel.getName());
//
//									if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI02"))
//									{
//										localMandatesModel.setCreditorName(localModel.getContactName());
//										localMandatesModel.setCreditorContactDetail(localModel.getPhoneNr());
//									}
//
//									if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI03"))
//									{
//										localMandatesModel.setUltimateCreditorName(localModel.getContactName());
//										localMandatesModel.setUltimateCreditorContactDetail(localModel.getPhoneNr());
//									}
//
//									if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI04"))
//									{
//										localMandatesModel.setDebtorName(localModel.getContactName());
//										localMandatesModel.setDebtorContactDetail(localModel.getPhoneNr());
//										localMandatesModel.setDebtorIdNo(localModel.getId());
//									}
//
//									if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI05"))
//									{
//										localMandatesModel.setUltimateDebtorName(localModel.getContactName());
//										localMandatesModel.setUltimateDebtorContactDetail(localModel.getPhoneNr());
//									}
//							}//end of for(PartyIdentList)
//						}//end of if(size > 0)
//
//
//
//						/**********************Retrieve Party Identification Info **********************/
//						List<CashAccountModel> localCashAccountList = retrieveCashAccount(mandateReqId);
//
//						if(localCashAccountList.size() > 0)
//						{
//							for (CashAccountModel localModel : localCashAccountList)
//							{
//								if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI02"))
//									localMandatesModel.setCreditorAccNo(localModel.getAccountNumber());
//
//								if(localModel.getPartyIdentTypeId().equalsIgnoreCase("PI04"))
//								{
//									localMandatesModel.setDebtorAccNo(localModel.getAccountNumber());
//									localMandatesModel.setDebtorAccName(localModel.getAccountName());
//								}
//							}//end of for(CashAccountList)
//						}//end of if (size >0 )
//
//
//
//						/**********************Retrieve Party Identification Info **********************/
//						List<FinInstModel> localFinInstList = retrieveFinInstitution(mandateReqId);
//
//						if(localFinInstList.size() > 0)
//						{
//							for (FinInstModel localModel : localFinInstList)
//							{
//								if(localModel.getFinInstTypeId().equalsIgnoreCase("FI04"))
//								{
//									localMandatesModel.setDebtorBank(localModel.getFiName());
//									localMandatesModel.setDebtorBranchNo(localModel.getBranchId());
//								}
//
//								if(localModel.getFinInstTypeId().equalsIgnoreCase("FI03"))
//								{
//									localMandatesModel.setCreditorBank(localModel.getFiName());
//									localMandatesModel.setCreditorBranchNo(localModel.getBranchId());
//								}
//							}//end of for(FinAccList)
//						}//end of if (size >0 )
//
//
//
//						tempMandatesList.add(localMandatesModel);
//				}//end of loop through list
//			}//end of size > 0
//
//			return tempMandatesList;
//		}//end of retrieveComplete...()
//


}
	

