package org.vebqa.vebtal.td;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IBANStore {

	public static final Logger logger = LoggerFactory.getLogger(IBANStore.class);
	
	private static final IBANStore store = new IBANStore();
	
	private IBANDriver ibanDriver;
	
	public IBANStore() {
		this.ibanDriver = new IBANDriver();
	}
	
	public static IBANStore getStore() {
		return store;
	}

	public IBANDriver getDriver() {
		return ibanDriver;
	}
	
}
