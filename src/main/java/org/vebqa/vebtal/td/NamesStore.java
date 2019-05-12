package org.vebqa.vebtal.td;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamesStore {

	public static final Logger logger = LoggerFactory.getLogger(NamesStore.class);
	
	private static final NamesStore store = new NamesStore();
	
	private NamesDriver namesDriver;
	
	public NamesStore() {
		this.namesDriver = new NamesDriver();
	}
	
	public static NamesStore getStore() {
		return store;
	}

	public NamesDriver getDriver() {
		return namesDriver;
	}
}
