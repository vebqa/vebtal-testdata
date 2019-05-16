package org.vebqa.vebtal.td;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.junit.rules.ExternalResource;
import org.vebqa.vebtal.model.Country;
import org.vebqa.vebtal.td.model.NameEntry;

import com.ancientprogramming.fixedformat4j.format.FixedFormatManager;
import com.ancientprogramming.fixedformat4j.format.impl.FixedFormatManagerImpl;

public class NamesDriver extends ExternalResource {

	private boolean loadedData;

	private String dataPath;
	
	private List<NameEntry> allNames;
	
	private static FixedFormatManager manager = new FixedFormatManagerImpl();

	public NamesDriver() {
		this.loadedData = false;
		this.allNames = new ArrayList<>();
		this.dataPath = "resources/names-data.txt";
	}

	public NamesDriver setDataPath(String aPath) {
		this.dataPath = aPath;
		return this;
	}

	public NamesDriver load() {
		loadData();
		if (this.allNames != null && this.allNames.size() > 0) {
			this.loadedData = true;
		}
		return this;
	}

	public NamesDriver close() {
		this.loadedData = false;
		this.allNames.clear();

		return this;
	}

	private void loadData() {
		File data = new File(this.dataPath);
		try (BufferedReader br = new BufferedReader(new FileReader(data))) {
			for (String line; (line = br.readLine()) != null;) {
				NameEntry record = manager.load(NameEntry.class, line);
				this.allNames.add(record);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getRecordCount() {
		return this.allNames.size();
	}

	public String getRandomFirstName(Country aCountry, String aGender) {
		Random r = new Random();
		List<NameEntry> filteredNames = new ArrayList<NameEntry>();
		
		// Filter Objects 
		for (NameEntry anEntry : this.allNames) {
			if (anEntry.getCountry().contains(aCountry.name()) ) {
				if (anEntry.getGender().contentEquals(aGender)) {
					filteredNames.add(anEntry);
				}
			}
		}
		return filteredNames.get(r.nextInt(filteredNames.size())).getName();
	}
	
	public String getRandomLastName(Country aCountry) {
		Random r = new Random();
		List<NameEntry> filteredNames = new ArrayList<NameEntry>();
		
		// Filter Objects 
		for (NameEntry anEntry : this.allNames) {
			if (anEntry.getCountry().contains(aCountry.name()) ) {
				filteredNames.add(anEntry);
			}
		}
		return filteredNames.get(r.nextInt(filteredNames.size())).getName();
	}
	
}
