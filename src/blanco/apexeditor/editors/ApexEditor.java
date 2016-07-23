package blanco.apexeditor.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class ApexEditor extends TextEditor {

	private ColorManager colorManager;

	public ApexEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new ApexConfiguration(colorManager));
		setDocumentProvider(new ApexDocumentProvider());
	}

	@Override
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
