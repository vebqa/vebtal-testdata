package org.vebqa.vebtal.td.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.td.NamesDriver;

public class GetnameTest {

	@Rule
	public final NamesDriver driver = new NamesDriver().load();

	@Test
	public void getFemaleFirstName() {
		Getname cmd = new Getname("getName", "name=f;gender=f", "");

		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(actualResult.getMessage(), Matchers.either(Matchers.is("Trillian")).or(Matchers.is("Karla")));
	}

	@Test
	public void getMaleFirstName() {
		Getname cmd = new Getname("getName", "name=f;gender=m", "");

		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(actualResult.getMessage(), Matchers.either(Matchers.is("Hugo")).or(Matchers.is("Ford")));
	}

	@Test
	public void getGermanLastName() {
		Getname cmd = new Getname("getName", "name=l;language=de", "");

		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(actualResult.getMessage(), Matchers.either(Matchers.is("Müller")).or(Matchers.is("Meier")));
	}

	@Test
	public void getEnglishLastName() {
		Getname cmd = new Getname("getName", "name=l;language=gb", "");

		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		assertThat(actualResult.getMessage(), Matchers.either(Matchers.is("Prefect")).or(Matchers.is("Beeblebrox")));
	}

	@Test
	public void testUnknownAttribute() {
		Getname cmd = new Getname("getName", "attribute=unknown", "");

		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.FAILED);
		assertThat(actualResult.getMessage(), Matchers.is("Unknown attribute: attribute"));
	}

}
