package org.vebqa.vebtal.td.commands;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.td.IBANDriver;
import org.vebqa.vebtal.tdrestserver.TDTestAdaptionPlugin;

/*
 * Please Note: When Createiban() method is called without an account number(account=)
 * in target, a random account number is generated with buildRandom(), which might not
 * generate a valid account number for the given country with correct Check Digit.
 */

@Keyword(module = TDTestAdaptionPlugin.ID, command = "createIBAN", description = "create a valid IBAN number", hintTarget = "country=;bank=;account=;", hintValue = "<storeKey")
public class Createiban extends AbstractCommand {

	public Createiban(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACCESSOR;
	}

	@Override
	public Response executeImpl(Object driver) {

		IBANDriver ibanDriver = (IBANDriver) driver;

		Response tResp = new Response();

		CountryCode country = CountryCode.DE;
		String bank = "";
		String account = "";

		// konvention: label=x
		String[] parts = target.split(";");
		for (String part : parts) {
			String[] subParts = part.split("=");
			switch (subParts[0]) {
			case "country":
				country = CountryCode.valueOf(subParts[1]);
				break;
			case "bank":
				bank = subParts[1];
				break;
			case "account":
				account = subParts[1];
				break;
			default:
				break;
			}
		}

		Iban iban = null;

		if (bank != "" && account != "") {
			iban = new Iban.Builder().countryCode(country).bankCode(bank).accountNumber(account).build();
		} else if (bank != "" && account == "") {
			iban = new Iban.Builder().countryCode(country).bankCode(bank).buildRandom();
		} else if (bank == "" && account != "") {
			iban = new Iban.Builder().countryCode(country).bankCode(ibanDriver.getRandomBLZfromData()).accountNumber(account).build();
		} else if (bank == "" && account == "") { //
			iban = new Iban.Builder().countryCode(country).bankCode(ibanDriver.getRandomBLZfromData()).buildRandom();
		}

		tResp.setCode(Response.PASSED);
		tResp.setMessage(iban.toFormattedString());
		if (this.value != null && !this.value.contentEquals("")) {
			tResp.setStoredValue(iban.toFormattedString());
			tResp.setStoredKey(this.value);
		}

		return tResp;
	}

}
