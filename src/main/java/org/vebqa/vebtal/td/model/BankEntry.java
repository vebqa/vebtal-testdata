package org.vebqa.vebtal.td.model;

import com.ancientprogramming.fixedformat4j.annotation.Field;
import com.ancientprogramming.fixedformat4j.annotation.Record;

/**
 * Standard model for bank records of the Bundesbankliste
 * @author doerges
 *
 */
@Record
public class BankEntry {

	private String bankleitzahl;
	private String blzDienstleister;
	private String bankName;
	private String plz;
	private String ort;
	private String shortName;
	private String pan;
	private String bic;
	private String crcmethod;
	private String numberDataEntry;
	private String changeAttribute;
	private String deprecation;
	private String successorBlz;
	
	@Field(offset=1, length=8)
	public String getBankleitzahl() {
		return bankleitzahl;
	}
	public void setBankleitzahl(String bankleitzahl) {
		this.bankleitzahl = bankleitzahl;
	}
	
	@Field(offset=9, length=1)
	public String getBlzDienstleister() {
		return blzDienstleister;
	}
	public void setBlzDienstleister(String blzDienstleister) {
		this.blzDienstleister = blzDienstleister;
	}
	
	@Field(offset=10, length=58)
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Field(offset=68, length=5)
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	
	@Field(offset=73, length=35)
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	
	@Field(offset=108, length=27)
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Field(offset=135, length=5)
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	
	@Field(offset=140, length=11)
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	
	@Field(offset=151, length=2)
	public String getCrcmethod() {
		return crcmethod;
	}
	public void setCrcmethod(String crcmethod) {
		this.crcmethod = crcmethod;
	}
	
	@Field(offset=153, length=6)
	public String getNumberDataEntry() {
		return numberDataEntry;
	}
	public void setNumberDataEntry(String numberDataEntry) {
		this.numberDataEntry = numberDataEntry;
	}
	
	@Field(offset=159, length=1)
	public String getChangeAttribute() {
		return changeAttribute;
	}
	public void setChangeAttribute(String changeAttribute) {
		this.changeAttribute = changeAttribute;
	}
	
	@Field(offset=160, length=1)
	public String getDeprecation() {
		return deprecation;
	}
	public void setDeprecation(String deprecation) {
		this.deprecation = deprecation;
	}
	
	@Field(offset=161, length=8)
	public String getSuccessorBlz() {
		return successorBlz;
	}
	public void setSuccessorBlz(String successorBlz) {
		this.successorBlz = successorBlz;
	}
}
