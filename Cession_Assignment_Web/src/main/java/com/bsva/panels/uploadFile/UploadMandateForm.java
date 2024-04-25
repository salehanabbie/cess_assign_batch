package com.bsva.panels.uploadFile;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;

import com.bsva.utils.Util;

/**
 * 
 * @author DimakatsoN
 *
 */

public class UploadMandateForm extends Panel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FileUploadField fileUpload;
	private Label branchLabel;
	private Button button;
	Util util;
	
	
	
	
	public UploadMandateForm(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		
		final FileUploadForm progressUploadForm = new FileUploadForm(
				"progressUpload");
		
		progressUploadForm.add(new UploadProgressBar("progress",
				progressUploadForm, progressUploadForm.fileUploadField));
		
		progressUploadForm.add(new FeedbackPanel("feedbackPanel"));
		add(progressUploadForm);
	}
	/**
	 * Form for uploads.
	 */
	private class FileUploadForm extends Form<Void> implements IAjaxIndicatorAware {
		
		FileUploadField fileUploadField;
		private final AjaxIndicatorAppender indicator;

		/**
		 * Construct.
		 * 
		 * @param name
		 *            Component name
		 */
		public FileUploadForm(String name) {
			super(name);

			indicator =  new AjaxIndicatorAppender();
			// set this form to multipart mode (always needed for uploads!)
			setMultiPart(true);

			// Add one file input field
			add(fileUploadField = new FileUploadField("fileInput"));

			// Set maximum size to 100K for demo purposes
			setMaxSize(Bytes.kilobytes(100));
			
			this.add(indicator);
		}

		@Override
		protected void onSubmit() {
			this.setEnabled(false);
			final List<org.apache.wicket.markup.html.form.upload.FileUpload> uploads = fileUploadField
					.getFileUploads();
			
			if (uploads != null) {
				for (org.apache.wicket.markup.html.form.upload.FileUpload upload : uploads) {
					// Create a new file
					
					String fileName = upload.getClientFileName();
					
					int fileNameExtension = fileName.indexOf(".");
					
					File newFilefile = new File(getUploadFolder(), upload.getClientFileName().subSequence(0, fileNameExtension -1) + "_online.xml");
					
//					File newFile = new File(getUploadFolder(),
//							upload.getClientFileName());

					try {
						// Save to new file
						
						
						
						newFilefile.createNewFile();
						upload.writeTo(newFilefile);
						
						info("File uploaded successfully. An email will be sent to confirm the processing status");
						
						
						
					} catch (Exception e) 
					{
						error("Unable to write file");
						throw new IllegalStateException("Unable to write file",
								e);
								
					}
					this.setEnabled(true);
				}
			}
		}

		private Folder getUploadFolder() {
			// TODO Auto-generated method stub
			return new Folder("/home/jboss/Mandates/Input/");
		}

		@Override
		public String getAjaxIndicatorMarkupId() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
