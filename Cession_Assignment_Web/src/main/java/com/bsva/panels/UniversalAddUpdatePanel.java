/**
 * 
 */
package com.bsva.panels;

import java.io.Serializable;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.bsva.models.UniversalModel;

/**
 * @author jeremym
 * 
 */
public class UniversalAddUpdatePanel extends Panel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Label code;
	private Label description;
	private TextField<String> codeInput;
	private TextField<String> descriptionInput;
	private Form<UniversalModel> form;
	private Button addButton; 
	private Button cancelButton; 
	private Button updateButton; 

	public UniversalAddUpdatePanel(String id, IModel<UniversalModel> model) {
		super(id, model);

		// Model Driven
		CompoundPropertyModel<UniversalModel> propertyModel = new 
				CompoundPropertyModel<UniversalModel>(model); 
		form = new Form<UniversalModel>("form",
				propertyModel);
		
		code = new Label("code");
		description = new Label("description");
		codeInput = new TextField<String>("codeInput");
		descriptionInput = new TextField<String>("descriptionInput");
		
		form.add(code);
		form.add(description);
		form.add(codeInput);
		form.add(descriptionInput);
		
		addButton = new Button("addButton"); 
		cancelButton = new Button("cancelButton"); 
		updateButton = new Button("updateButton"); 
		
		form.add(addButton); 
		form.add(updateButton); 
		form.add(cancelButton); 
		
		if (propertyModel.getObject().isUpdate()) {
			addButton.setVisibilityAllowed(false);
			addButton.setVisible(false);
		}else{
			updateButton.setVisibilityAllowed(false); 
			updateButton.setVisible(false);
		}
		
		add(form);

	}

}
