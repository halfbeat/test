package es.jcyl.cs.extractor.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Configuration {
	public final static String CONFIG_FILE = "configuration.properties";

	private static PropertiesConfiguration instance = null;

	public static PropertiesConfiguration getConfiguration() throws ConfigurationException {
		if (instance == null) {
			instance = new PropertiesConfiguration(Thread.currentThread().getContextClassLoader().getResource(
				CONFIG_FILE));
		}
		return instance;
	}

	public static PropertiesConfiguration getConfiguration(String filename) throws ConfigurationException {
		if (instance == null) {
			instance = new PropertiesConfiguration(Thread.currentThread().getContextClassLoader().getResource(
				filename));
		}
		return instance;
	}
}
