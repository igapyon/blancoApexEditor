package blanco.apexeditor.editors;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

public class ApexRule extends WordRule implements IPredicateRule {
	private IToken fSuccessTokenTotal;

	private IToken fSuccessKeywordToken = null;

	private IToken fSuccessSystemClassesToken = null;

	public ApexRule(IToken successKeywordToken, String[] apexKeywords, IToken successSystemClassesToken,
			String[] apexSystemClasses) {
		super(new BlancoApexDetector(), new Token(ApexPartitionScanner.APEX_DEFAULT));
		fSuccessKeywordToken = successKeywordToken;
		for (int i = 0; i < apexKeywords.length; i++) {
			addWord(apexKeywords[i], successKeywordToken);
			addWord(apexKeywords[i].toLowerCase(), successKeywordToken);
		}
		fSuccessSystemClassesToken = successSystemClassesToken;
		for (int i = 0; i < apexSystemClasses.length; i++) {
			addWord(apexSystemClasses[i], successSystemClassesToken);
			addWord(apexSystemClasses[i].toLowerCase(), successSystemClassesToken);
		}
	}

	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}

	public IToken evaluate(ICharacterScanner scanner) {
		IToken result = super.evaluate(scanner);
		if (result == fSuccessKeywordToken) {
			fSuccessTokenTotal = result;
		}
		if (result == fSuccessSystemClassesToken) {
			fSuccessTokenTotal = result;
		}
		return result;
	}

	public IToken getSuccessToken() {
		return fSuccessTokenTotal;
	}

	/**
	 * Detect word
	 * 
	 * @author Toshiki Iga
	 */
	static class BlancoApexDetector implements IWordDetector {
		public boolean isWordStart(char character) {
			return ApexRule.isLetter(character);
		}

		public boolean isWordPart(char character) {
			return (ApexRule.isLetter(character) || ApexRule.isDigit(character));
		}
	}

	/**
	 * Check space or not
	 * 
	 * @param argChar
	 * @return
	 * @author Toshiki Iga
	 */
	public static boolean isSpace(final char argChar) {
		// 2005.08.12 Tosiki Iga 65535(original -1) is from Eclipse.
		return argChar == ' ' || argChar == '\t' || argChar == '\n' || argChar == '\r' || argChar == 65535;
	}

	/**
	 * Check letter or not.
	 * 
	 * @param argChar
	 * @return
	 */
	public static boolean isLetter(final char argChar) {
		// treat _ as member of letter
		// || (c == '_' || c == '#');
		if (isSpace(argChar)) {
			return false;
		}
		if (isDigit(argChar)) {
			return false;
		}
		if (isSymbol(argChar)) {
			return false;
		}
		return true;
	}

	/**
	 * Check digit or not.
	 * 
	 * @param argChar
	 * @return
	 */
	public static boolean isDigit(final char argChar) {
		return '0' <= argChar && argChar <= '9';
	}

	/**
	 * Check symbole or not.
	 * 
	 * @param argChar
	 * @return
	 */
	public static boolean isSymbol(final char argChar) {
		switch (argChar) {
		case '"': // double quote
		case '?': // question mark
		case '%': // percent
		case '&': // ampersand
		case '\'': // quote
		case '(': // left paren
		case ')': // right paren
		case '|': // vertical bar
		case '*': // asterisk
		case '+': // plus sign
		case ',': // comma
		case '-': // minus sign
		case '.': // period
		case '/': // solidus
		case ':': // colon
		case ';': // semicolon
		case '<': // less than operator
		case '=': // equals operator
		case '>': // greater than operator

			// sometime # is member of char: case '#':
			// treat _ as char case '_': //underscore
			// skipping below:
			// case '!':
			// case '$':
			// case '[':
			// case '\\':
			// case ']':
			// case '^':
			// case '{':
			// case '}':
			// case '~':
			return true;
		default:
			return false;
		}
	}
}
