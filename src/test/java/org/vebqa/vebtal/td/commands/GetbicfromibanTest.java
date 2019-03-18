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
		Getbicfromiban cmd = new Getbicfromiban("GetBICFromIBAN", "DE29100000029935397941", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(actualResult.getMessage(), containsString("ENTBKAA1100"));
	}

	@Test
	public void getBICfromIBANandFailInvalidCheckDigit() {
		Getbicfromiban cmd = new Getbicfromiban("GetBICFromIBAN", "DE29100000129935397941", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.FAILED);
		assertThat(actualResult.getMessage(), containsString("Invalid check digit"));
	}
	
	@Test
	public void getBICfromIBANandFailNotExisting() {
		Getbicfromiban cmd = new Getbicfromiban("GetBICFromIBAN", "DE63523500053483117914", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.FAILED);
		assertThat(actualResult.getMessage(), containsString("Cannot resolve BIC"));
	}	
}
