package org.vebqa.vebtal.td.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.vebqa.vebtal.model.Response;

public class CreateibanTest {

	@Test
	public void generateIBAN() {
		Createiban cmd = new Createiban("createIBAN", "", "");
		
		Response actualResult = cmd.executeImpl(new Object());

		Response expectedResult = new Response();
		expectedResult.setCode(Response.PASSED);
		expectedResult.setMessage("");
		
		assertThat(expectedResult, samePropertyValuesAs(actualResult));
	}

}
