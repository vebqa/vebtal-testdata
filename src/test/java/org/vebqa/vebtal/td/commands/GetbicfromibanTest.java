package org.vebqa.vebtal.td.commands;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.td.IBANDriver;

public class GetbicfromibanTest {

	@Rule
	public final IBANDriver driver = new IBANDriver().setDataPath("./resources/blz-test-txt-data.txt").load();

	@Test
	public void getExistingBICfromIBAN() {
		Getbicfromiban cmd = new Getbicfromiban("getBICFromIBAN", "DE43360102001053792200", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(actualResult.getMessage(), containsString("VONEDE33XXX"));
	}

	@Test
	public void getBICfromIBANFailWithInvalidCheckDigit() {
		Getbicfromiban cmd = new Getbicfromiban("getBICFromIBAN", "DE29100000129935397941", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.FAILED);
		assertThat(actualResult.getMessage(), containsString("Invalid check digit"));
	}
	
	@Test
	public void getBICfromIBANFailWhileBICWasNull() {
		Getbicfromiban cmd = new Getbicfromiban("getBICFromIBAN", "DE63523500053483117914", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.FAILED);
		assertThat(actualResult.getMessage(), containsString("Cannot resolve BIC"));
	}
	
	@Test
	public void getBICfromIBANFailWithInvalidFormat() {
		Getbicfromiban cmd = new Getbicfromiban("getBICFromIBAN", "DE2FT129935397941", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.FAILED);
		assertThat(actualResult.getMessage(), containsString("Invalid format of IBAN"));
	}
	
	@Test
	public void getBICfromIBANFailWithUnsupportedCountry() {
		Getbicfromiban cmd = new Getbicfromiban("getBICFromIBAN", "IQ98NBIQ657453423456748", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.FAILED);
		assertThat(actualResult.getMessage(), containsString("Unsupported country"));
	}
	
	@Test
	public void getBICfromIBANFailWithBanksOtherThanGerman() {
		Getbicfromiban cmd = new Getbicfromiban("getBICFromIBAN", "FR1420041010050500013M02606", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.FAILED);
		assertThat(actualResult.getMessage(), containsString("Only german banks can be resolved to BIC at this moment."));
	}
	
}
