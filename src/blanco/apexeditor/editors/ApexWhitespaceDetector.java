package blanco.apexeditor.editors;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class ApexWhitespaceDetector implements IWhitespaceDetector {

	@Override
	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
