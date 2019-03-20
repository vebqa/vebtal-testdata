package org.vebqa.vebtal.td.commands;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
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
		String iban = actualResult.getMessage();

		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(iban, CoreMatchers.anyOf(containsString("3601 0200"), containsString("7603 0080")));
		assertThat(iban, containsString("DE"));

		System.out.println("Random: " + iban);
	}

	@Test
	public void generateIBANWithBankCode() {
		Createiban cmd = new Createiban("createIBAN", "bank=76030080", "");

		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(actualResult.getMessage(), CoreMatchers.allOf(containsString("7603 0080"), containsString("DE")));

		System.out.println("With Bank Code: " + actualResult.getMessage());
	}

	@Test
	public void generateIBANWithAccountNumber() {
		Createiban cmd = new Createiban("createIBAN", "account=6158695567", "");

		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(actualResult.getMessage(), CoreMatchers.allOf(containsString("6158 6955 67"), containsString("DE")));

		System.out.println("With Account Number: " + actualResult.getMessage());
	}

	@Test
	public void generateIBANWithBankCodeAndAccountNumber() {
		Createiban cmd = new Createiban("createIBAN", "bank=36010200;account=1378304570;", "");

		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(actualResult.getMessage(),
				CoreMatchers.allOf(containsString("3601 0200"), containsString("1378 3045 70"), containsString("DE")));

		System.out.println("With Bank Code & Account Number: " + actualResult.getMessage());
	}

}