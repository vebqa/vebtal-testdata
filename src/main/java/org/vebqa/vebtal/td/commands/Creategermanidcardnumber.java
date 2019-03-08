package org.vebqa.vebtal.td.commands;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.tdrestserver.TDTestAdaptionPlugin;

@Keyword(module = TDTestAdaptionPlugin.ID, command = "createGermanIDCardNumber", description = "create a valid id card number", hintTarget = "blz=;lfd=;nation=;dob=;expire=;", hintValue = "<storeKey")
public class Creategermanidcardnumber extends AbstractCommand {

	private final Logger logger = LoggerFactory.getLogger(Creategermanidcardnumber.class);
	
	public Creategermanidcardnumber(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACCESSOR;
	}

	@Override
	public Response executeImpl(Object driver) {

		Response tResp = new Response();

		String blz = "L808";
		String lfd = "";
		String nation = "D";
		String dob = "760529"; // 29.05.1976
		String expire = "250722"; // 22.07.2025

		// konvention: label=x
		String[] parts = target.split(";");
		for (String part : parts) {
			String[] subParts = part.split("=");
			switch (subParts[0]) {
			case "blz":
				blz = subParts[1];
				break;
			case "lfd":
				lfd = subParts[1];
				break;
			case "nation":
				nation = subParts[1];
				break;
			case "dob":
				dob = subParts[1];
				break;
			case "expire":
				expire = subParts[1];
				break;

			default:
				break;
			}
		}

		// Number generieren?
		if (lfd.contentEquals("")) {
			Random rand = new Random();
			lfd = String.format("%05d", rand.nextInt(10000));
		}

		String term1="";
		String term2="";
		String term3="";
		String term4="";
		String term5="";
		
		if ((blz.length() == 4) && (lfd.length() == 5) && (nation.length() == 1) && (dob.length() == 6)
				&& (expire.length() == 6)) {
			term1 = blz + lfd + checksum(blz + lfd);
			term2 = nation;
			term3 = dob + checksum(dob);
			term4 = expire + checksum(expire);
			term5 = String.valueOf(checksum(term1 + term3 + term4));
			logger.info(term1 + " - " + term2 + " - " + term3 + " - " + term4 + " - " + term5);
		} else {
			logger.info("cannot calculate chechsum, because of input error: {},{},{},{},{}", blz, lfd, nation, dob, expire);
		}

		tResp.setCode(Response.PASSED);
		tResp.setMessage(term1 + "-" + term2 + "-" + term3 + "-" + term4 + "-" + term5);
		tResp.setStoredValue(term1 + term2 + term5);
		tResp.setStoredKey(this.value);

		return tResp;
	}

	public int checksum(String anInput) {
		int cs = 0;
		int i = 1;
		final int len = anInput.length();
		for (int pos = 0; pos < len; pos++) {

			int charOrd;

			switch (i) {
			case 1:
				charOrd = (int) anInput.substring(pos, pos + 1).charAt(0);
				charOrd = charOrd - 48;
				charOrd = charOrd * 7;
				cs = cs + charOrd;
				i++;
				break;
			case 2:
				charOrd = (int) anInput.substring(pos, pos + 1).charAt(0);
				charOrd = charOrd - 48;
				charOrd = charOrd * 3;
				cs = cs + charOrd;
				i++;
				break;
			case 3:
				charOrd = (int) anInput.substring(pos, pos + 1).charAt(0);
				charOrd = charOrd - 48;
				charOrd = charOrd * 1;
				cs = cs + charOrd;
				i = 1;
				break;
			}
		}
		cs = cs % 10;
		return cs;
	}
}
