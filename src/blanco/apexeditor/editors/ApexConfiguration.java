package blanco.apexeditor.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class ApexConfiguration extends SourceViewerConfiguration {
	private ApexDoubleClickStrategy doubleClickStrategy;
	private ApexTagScanner tagScanner;
	private ApexScanner scanner;
	private ColorManager colorManager;

	public ApexConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE, ApexPartitionScanner.APEX_COMMENT,
				ApexPartitionScanner.APEX_KEYWORD, ApexPartitionScanner.APEX_SYSTEM_CLASSES };
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new ApexDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected ApexScanner getApexScanner() {
		if (scanner == null) {
			scanner = new ApexScanner(colorManager);
			scanner.setDefaultReturnToken(
					new Token(new TextAttribute(colorManager.getColor(IApexColorConstants.DEFAULT))));
		}
		return scanner;
	}

	protected ApexTagScanner getApexTagScanner() {
		if (tagScanner == null) {
			tagScanner = new ApexTagScanner(colorManager);
			tagScanner.setDefaultReturnToken(
					new Token(new TextAttribute(colorManager.getColor(IApexColorConstants.KEYWORD))));
		}
		return tagScanner;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getApexTagScanner());
		reconciler.setDamager(dr, ApexPartitionScanner.APEX_KEYWORD);
		reconciler.setRepairer(dr, ApexPartitionScanner.APEX_KEYWORD);

		dr = new DefaultDamagerRepairer(getApexScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
				new TextAttribute(colorManager.getColor(IApexColorConstants.APEX_COMMENT)));
		reconciler.setDamager(ndr, ApexPartitionScanner.APEX_COMMENT);
		reconciler.setRepairer(ndr, ApexPartitionScanner.APEX_COMMENT);

		return reconciler;
	}

}