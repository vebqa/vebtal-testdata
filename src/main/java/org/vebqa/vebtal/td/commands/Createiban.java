package org.vebqa.vebtal.td.commands;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.tdrestserver.TDTestAdaptionPlugin;

@Keyword(module = TDTestAdaptionPlugin.ID, command = "createIBAN", description = "create a valid IBAN number", hintTarget = "country=;bank=;account=;", hintValue = "<storeKey")
public class Createiban extends AbstractCommand {

	public Createiban(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACCESSOR;
	}

	@Override
	public Response executeImpl(Object driver) {
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

		Iban iban = new Iban.Builder().countryCode(country).buildRandom();

		tResp.setCode(Response.PASSED);
		tResp.setMessage(iban.toFormattedString());
		tResp.setStoredValue(iban.toFormattedString());
		tResp.setStoredKey(this.value);

		return tResp;
	}

}
