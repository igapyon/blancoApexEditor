package blanco.apexeditor.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

public class ApexScanner extends RuleBasedScanner {

	public ApexScanner(ColorManager manager) {
		IRule[] rules = new IRule[4];
		// Add rule for processing instructions
		rules[0] = new SingleLineRule("\"", "\"",
				new Token(new TextAttribute(manager.getColor(IApexColorConstants.STRING))), '\\');
		rules[1] = new SingleLineRule("\'", "\'",
				new Token(new TextAttribute(manager.getColor(IApexColorConstants.STRING))), '\\');
		rules[2] = new ApexRule(new Token(new TextAttribute(manager.getColor(IApexColorConstants.KEYWORD))),
				ApexEditorConstants.RESERVED_KEYWORDS,
				new Token(new TextAttribute(manager.getColor(IApexColorConstants.APEX_SYSTEMCLASSES))),
				ApexEditorConstants.APEX_SYSTEM_CLASSES);
		rules[3] = new WhitespaceRule(new ApexWhitespaceDetector());

		setRules(rules);
	}
}
