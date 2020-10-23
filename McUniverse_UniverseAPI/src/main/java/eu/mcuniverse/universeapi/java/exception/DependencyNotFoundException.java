package eu.mcuniverse.universeapi.java.exception;

import org.apache.commons.lang.WordUtils;

public class DependencyNotFoundException extends UnsupportedOperationException {

	private static final long serialVersionUID = 7600345223609589144L;

	public DependencyNotFoundException(String softDepend) {
		super(WordUtils.capitalize(softDepend).trim() + " not found but accessing features from it!");
	}
	
	
	
}
