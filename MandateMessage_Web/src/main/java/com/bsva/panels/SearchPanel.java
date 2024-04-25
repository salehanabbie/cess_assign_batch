/**
 * 
 */
package com.bsva.panels;

import java.io.Serializable;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.bsva.models.SearchModel;

/**
 * @author jeremym
 * 
 */
public class SearchPanel extends Panel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Form<SearchModel> form;
	private TextField<String> searchCriteria;
	private Label searchName;
	
	
	public SearchPanel(String id, IModel<SearchModel> model) {
		super(id, model);

	form = new Form<SearchModel>("searchForm", new CompoundPropertyModel<SearchModel>(model));
	searchCriteria = new TextField<String>("searchCriteria");
	searchName = new Label("searchName");
	
	form.add(searchCriteria); 
	form.add(searchName);
	
	add(form);
	
	
	}

}
