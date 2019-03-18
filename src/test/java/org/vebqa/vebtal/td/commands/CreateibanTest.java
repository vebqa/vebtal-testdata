package org.vebqa.vebtal.td.commands;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.td.IBANDriver;

public class CreateibanTest {

	@Rule
	public final IBANDriver driver = new IBANDriver().setDataPath("./resources/blz-test-txt-data.txt").load();

	@Test
	public void generateIBAN() {
		Createiban cmd = new Createiban("createIBAN", "", "");
		
		Response actualResult = cmd.executeImpl(driver);

		Response expectedResult = new Response();
		expectedResult.setCode(Response.PASSED);
		expectedResult.setMessage("DE29 1000 0002 9935 3979 41");
		
		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(actualResult.getMessage(), containsString("1000 0002"));
	}

}
