package org.vebqa.vebtal.td.commands;

import java.util.Locale;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.td.NamesDriver;
import org.vebqa.vebtal.tdrestserver.TDTestAdaptionPlugin;

/*
 * Note: When getName is called without a scope of the name (first, last) it will return a full name
 */

@Keyword(module = TDTestAdaptionPlugin.ID, command = "getName", description = "get a name", hintTarget = "name=;gender=", hintValue = "<storeKey")
public class Getname extends AbstractCommand {

	public Getname(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACCESSOR;
	}

	@Override
	public Response executeImpl(Object driver) {

		NamesDriver aDriver = (NamesDriver) driver;

		Response tResp = new Response();

		String gender = "";
		String name = "";
		String language = "";

		// konvention: label=x
		String[] parts = target.split(";");
		for (String part : parts) {
			String[] subParts = part.split("=");
			switch (subParts[0]) {
			case "name":
				name = subParts[1];
				break;
			case "gender":
				gender = subParts[1];
				break;
			case "language":
				language = subParts[1];
				break;
			default:
				tResp.setCode(Response.FAILED);
				tResp.setMessage("Unknown attribute: " + subParts[0]);
				return tResp;
			}
		}

		String aLanguage = "";
		if (language != "") {
			String[] locales = Locale.getISOCountries();

			for (String countryCode : locales) {
				if (language.equalsIgnoreCase(countryCode)) {
					aLanguage = countryCode;
				}
			}
		}

		String aGeneratedName = "";

		if (name.contentEquals("f")) {
			aGeneratedName = aDriver.getRandomFirstName(aLanguage, gender);
		}
		if (name.contentEquals("l")) {
			aGeneratedName = aDriver.getRandomLastName(aLanguage);
		}

		tResp.setCode(Response.PASSED);
		tResp.setMessage(aGeneratedName);
		if (this.value != null && !this.value.contentEquals("")) {
			tResp.setStoredValue(aGeneratedName);
			tResp.setStoredKey(this.value);
		}

		return tResp;
	}
}
