package org.vebqa.vebtal.td;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.rules.ExternalResource;
import org.vebqa.vebtal.td.model.BankEntry;

import com.ancientprogramming.fixedformat4j.format.FixedFormatManager;
import com.ancientprogramming.fixedformat4j.format.impl.FixedFormatManagerImpl;

public class IBANDriver extends ExternalResource {

	private boolean loadedblzdata;

	private String actualDataPath = "";

	private List<BankEntry> allDeBanks;

	private List<BankEntry> allDeCreateAccountBanks;
	
	private static FixedFormatManager manager = new FixedFormatManagerImpl();

	public IBANDriver() {
		this.loadedblzdata = false;
		this.allDeBanks = new ArrayList<>();
		this.allDeCreateAccountBanks = new ArrayList<>();
	}

	public IBANDriver setDataPath(String aPath) {
		this.actualDataPath = aPath;
		return this;
	}

	public IBANDriver load() {
		loadData();
		if (this.allDeBanks != null && this.allDeBanks.size() > 0) {
			this.loadedblzdata = true;
		}
		return this;
	}

	public IBANDriver close() {
		this.loadedblzdata = false;
		this.allDeBanks.clear();

		return this;
	}

	private void loadData() {
		File data = new File(this.actualDataPath);
		try (BufferedReader br = new BufferedReader(new FileReader(data))) {
			for (String line; (line = br.readLine()) != null;) {
				BankEntry record = manager.load(BankEntry.class, line);
				this.allDeBanks.add(record);
				// load banks without special crc only: key => 09
				if (record.getCrcmethod().contentEquals("09")) {
					this.allDeCreateAccountBanks.add(record);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getRecordCount() {
		return this.allDeBanks.size();
	}

	public String getBICbyBLZ(String aBLZ) {
		for (BankEntry anEntry : this.allDeBanks) {
			if (anEntry.getBankleitzahl().contentEquals(aBLZ)) {
				return anEntry.getBic();
			}
		}
		return null;
	}

	public String getRandomBLZfromData() {
		Random r = new Random();
		return this.allDeBanks.get(r.nextInt(this.getRecordCount())).getBankleitzahl();
	}
	
	public String getRandomBLZfromDataForAccountCreation() {
		Random r = new Random();
		return this.allDeCreateAccountBanks.get(r.nextInt(this.allDeCreateAccountBanks.size())).getBankleitzahl();
	}
}
