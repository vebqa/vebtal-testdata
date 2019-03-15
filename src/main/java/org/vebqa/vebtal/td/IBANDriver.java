package org.vebqa.vebtal.td;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.ExternalResource;
import org.vebqa.vebtal.td.model.BankEntry;

public class IBANDriver extends ExternalResource {

	private boolean loadedblzdata;
	
	private List<BankEntry> allDeBanks;
	
	public IBANDriver() {
		this.loadedblzdata = false;
		this.allDeBanks = new ArrayList<>();
	}
}
