/**
 * 
 */
package com.bsva.panels;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.bsva.models.MandateModel;

/**
 * @author jeremym
 *
 */
public class MandatePanel extends Panel{

	private static final long serialVersionUID = 1L;

	public MandatePanel(String id, IModel<MandateModel> model) {
		super(id, model);
		
		Form<MandateModel> form = new Form<MandateModel>("form", new CompoundPropertyModel<MandateModel>(model)); 

		// Labels 
		form.add(new Label("messageId", "Message Id"));
		form.add(new Label("creditorName", "Creditor Name"));
		form.add(new Label("mandateId", "Mandate Id"));
		form.add(new Label("localInstrument", "Local Instrument"));
		form.add(new Label("fromDate", "From Date"));
		form.add(new Label("firstCollectionDate", "First Collection Date"));
		form.add(new Label("collectionAmount", "Collection Amount"));
		form.add(new Label("creditorSchemaName", "Creditor Schema Name"));
		form.add(new Label("contactDetail", "Contact Detail"));
		form.add(new Label("creditorBranchName", "Creditor Branch Name"));
		
		form.add(new Label("creditorAccountNumber", "Creditor Account Number"));
		form.add(new Label("ultimateCreditorName", "Ultimate Creditor Name"));
		form.add(new Label("debtorAccountName", "Debtor Account Name"));
		form.add(new Label("debtorBranchNumber", "Debtor Branch Number"));
		form.add(new Label("ultimateDebtorName", "Ultimate Debtor Name"));
		form.add(new Label("memberNumber", "Member Number"));
		form.add(new Label("mandateRequestId", "Mandate Request Id"));
		form.add(new Label("frequency", "Frequency"));
		form.add(new Label("toDate", "To Date"));
		form.add(new Label("finalCollectionDate", "Final Collection Date"));
		
		form.add(new Label("maximumAmount", "Maximum Amount"));
//		form.add(new Label("creditorName", "Creditor Name"));
		form.add(new Label("creditorBank", "Creditor Bank"));
//		form.add(new Label("contactDetail", "Contact Detail"));
		form.add(new Label("debtorAccountNumber", "Debtor Account Number"));
		form.add(new Label("debtorBank", "Debtor Bank"));
		
		// TextFields
		form.add(new TextField<String>("messageIdTextField"));
		form.add(new TextField<String>("creditorNameTextField"));
		form.add(new TextField<String>("mandateIdTextField"));
		form.add(new TextField<String>("localInstrumentTextField"));
		form.add(new TextField<String>("fromDateTextField"));
		form.add(new TextField<String>("firstCollectionDateTextField"));
		form.add(new TextField<String>("collectionAmountTextField"));
		form.add(new TextField<String>("creditorSchemaNameTextField"));
		form.add(new TextField<String>("contactDetailTextField"));
		form.add(new TextField<String>("creditorBranchNameTextField"));
		
		form.add(new TextField<String>("creditorAccountNumberTextField"));
		form.add(new TextField<String>("ultimateCreditorNameTextField"));
		form.add(new TextField<String>("debtorAccountNameTextField"));
		form.add(new TextField<String>("debtorBranchNumberTextField"));
		form.add(new TextField<String>("ultimateDebtorNameTextField"));
		form.add(new TextField<String>("memberNumberTextField"));
		form.add(new TextField<String>("mandateRequestIdTextField"));
		form.add(new TextField<String>("frequencyTextField"));
		form.add(new TextField<String>("toDateTextField"));
		form.add(new TextField<String>("finalCollectionDateTextField"));
		
		form.add(new TextField<String>("maximumAmountTextField"));
		form.add(new TextField<String>("creditorBankTextField"));
		form.add(new TextField<String>("debtorAccountNumberTextField"));
		form.add(new TextField<String>("debtorBankTextField"));

		

		
		add(form); 
		
	}

	
	
	
	
}
