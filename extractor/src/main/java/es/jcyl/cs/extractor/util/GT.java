package es.jcyl.cs.extractor.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class GT {
	private static final String BUNDLE_NAME = "es.jcyl.cs.extractor.util.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	
	private static final Object noargs[] = new Object[0];

	private GT() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static final String tr(String message) {
		return translate(message, null);
	}

	public static final String tr(String message, Object arg) {
		return translate(message, new Object[] { arg });
	}

	public static final String tr(String message, Object args[]) {
		return translate(message, args);
	}
	
	private static final String translate(String message, Object args[]) {
		if (RESOURCE_BUNDLE != null && message != null)
			try {
				message = RESOURCE_BUNDLE.getString(message);
			} catch (MissingResourceException mre) {
			}
		if (args == null)
			args = noargs;
		if (message != null)
			message = MessageFormat.format(message, args);
		return message;
	}
}
