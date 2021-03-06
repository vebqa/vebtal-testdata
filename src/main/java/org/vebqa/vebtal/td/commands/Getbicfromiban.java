package org.vebqa.vebtal.td.commands;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.iban4j.IbanFormat;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.td.IBANDriver;
import org.vebqa.vebtal.tdrestserver.TDTestAdaptionPlugin;

@Keyword(module = TDTestAdaptionPlugin.ID, command = "getBicfromIBAN", description = "validate a given IBAN number and return BIC", hintTarget = "<IBAN>", hintValue = "<buffer>")
public class Getbicfromiban extends AbstractCommand {

	public Getbicfromiban(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACCESSOR;
	}

	@Override
	public Response executeImpl(Object driver) {

		IBANDriver ibanDriver = (IBANDriver) driver;

		Response tResp = new Response();

		Iban iban;

		try {
			if (this.target.contains(" ")) {
				IbanUtil.validate(this.target, IbanFormat.Default);
				iban = Iban.valueOf(this.target, IbanFormat.Default);
			} else {
				IbanUtil.validate(this.target);
				iban = Iban.valueOf(this.target);
			}
			tResp.setCode(Response.PASSED);
		} catch (IbanFormatException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Invalid format of IBAN");
			return tResp;
		} catch (InvalidCheckDigitException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Invalid check digit");
			return tResp;
		} catch (UnsupportedCountryException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Unsupported country");
			return tResp;
		}

		if (iban.getCountryCode() != CountryCode.DE) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Only german banks can be resolved to BIC at this moment.");
			return tResp;
		}

		String tBIC = ibanDriver.getBICbyBLZ(iban.getBankCode());

		if (tBIC != null) {
			tResp.setCode(Response.PASSED);
			tResp.setMessage(tBIC);
			if (this.value != null && !this.value.contentEquals("")) {
				tResp.setStoredKey(this.value);
				tResp.setStoredValue(tBIC);
			}
		} else {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Cannot resolve BIC");
		}

		return tResp;

	}

}
