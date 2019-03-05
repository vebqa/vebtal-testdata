package org.vebqa.vebtal.td.commands;

import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.tdrestserver.TDTestAdaptionPlugin;

@Keyword(module = TDTestAdaptionPlugin.ID, command = "CreateGermanIDCardNumber", description = "create a valid id card number", hintTarget = "blz=;number=;nation=;dob=;expire=;", hintValue = "<storeKey")
public class Creategermanidcardnumber extends AbstractCommand {

	public Creategermanidcardnumber(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACCESSOR;
	}

	@Override
	public Response executeImpl(Object driver) {

		Response tResp = new Response();
		
		String blz = "L808";
		String number = "0123";
		String nation = "D";
		String dob = "1990";
		String expire = "2020";

		// konvention: label=x
		String[] parts = target.split("=");
		for (String part : parts) {
			switch (part) {
			case "blz":
				blz = part;
				break;
			case "number":
				number = part;
				break;
			case "nation":
				nation = part;
				break;
			case "dob":
				dob = part;
				break;
			case "expire":
				expire = part;
				break;
			
				
			default:
				break;
			}
		}

		tResp.setCode(Response.PASSED);
		tResp.setStoredValue("");
		tResp.setStoredKey(value);

		return tResp;
	}
}
