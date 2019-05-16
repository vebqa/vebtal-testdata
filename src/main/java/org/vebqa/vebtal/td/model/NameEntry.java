package org.vebqa.vebtal.td.model;

import com.ancientprogramming.fixedformat4j.annotation.Field;
import com.ancientprogramming.fixedformat4j.annotation.Record;

/**
 * Standard model for name records
 * @author doerges
 *
 */
@Record
public class NameEntry {

	private String name;
	private String type;   // f(irst), l(last)
	private String gender; // m, f
	private String country; // de, en, fr, ..
	
	@Field(offset=1, length=24)
	public String getName() {
		return this.name;
	}
	public void setName(String aName) {
		this.name = aName;
	}
	
	@Field(offset=25, length=1)
	public String getType() {
		return this.type;
	}
	public void setType(String aType) {
		this.type = aType;
	}
	
	@Field(offset=27, length=1)
	public String getGender() {
		return this.gender;
	}
	public void setGender(String aGender) {
		this.gender = aGender;
	}
	
	@Field(offset=29, length=1)
	public String getCountry() {
		return this.country;
	}
	
	public void setCountry(String aGender) {
		this.country = aGender;
	}
}
