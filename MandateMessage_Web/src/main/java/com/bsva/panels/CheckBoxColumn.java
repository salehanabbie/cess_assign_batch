package com.bsva.panels;

import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.MarkupFragment;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * @author MargaretM
 *
 */
public abstract class CheckBoxColumn<T> extends AbstractColumn<T, T> {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(CheckBoxColumn.class);

	public CheckBoxColumn(IModel<String> displayModel) {
		super(displayModel);
	}

	public void populateItem(Item<ICellPopulator<T>> cellItem,
			String componentId, IModel<T> rowModel) {
		cellItem.add(new CheckPanel(componentId, newCheckBoxModel(rowModel)));
	}

	protected AjaxCheckBox newCheckBox(String id, IModel<Boolean> checkModel) {
		AjaxCheckBox checkBox = new AjaxCheckBox("check", checkModel) {

			@Override
			protected void onUpdate(AjaxRequestTarget arg0) {
				if (getModelObject()) {
					MarkupContainer markupContainer = getParent();

					log.debug(markupContainer);

				}

				log.debug(getModelValue());
			}

		};
		return checkBox;
	}

	protected abstract IModel<Boolean> newCheckBoxModel(IModel<T> rowModel);

	private class CheckPanel extends Panel {
		private static final long serialVersionUID = 1L;

		public CheckPanel(String id, IModel<Boolean> checkModel) {
			super(id);
			Form form = new Form("checkPanelForm");
			form.add(newCheckBox("check", checkModel));
			add(form);
		}
	}
}
