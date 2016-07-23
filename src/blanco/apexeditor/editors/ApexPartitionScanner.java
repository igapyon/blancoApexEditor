package blanco.apexeditor.editors;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class ApexPartitionScanner extends RuleBasedPartitionScanner {
	public final static String APEX_DEFAULT = "__apex_default";

	public final static String APEX_KEYWORD = "__keyword_tag";

	public final static String APEX_SYSTEM_CLASSES = "__systemclasses_tag";

	public final static String APEX_STRING = "__apex_string";

	public final static String APEX_COMMENT = "__apex_comment";

	public ApexPartitionScanner() {
		IPredicateRule[] rules = new IPredicateRule[2];
		rules[0] = new MultiLineRule("/*", "*/", new Token(APEX_COMMENT));
		// Apex single line comment.
		rules[1] = new EndOfLineRule("//", new Token(APEX_COMMENT));

		setPredicateRules(rules);
	}
}
