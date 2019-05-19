package org.vebqa.vebtal.td.commands;

import static org.junit.Assert.assertEquals;

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
		// assertThat(actualResult.getMessage(), );
	}

	@Test
	public void getMaleFirstName() {
		Getname cmd = new Getname("getName", "name=f;gender=m", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		// assertThat(actualResult.getMessage(), );
	}

	@Test
	public void getGermanLastName() {
		Getname cmd = new Getname("getName", "name=l;country=de", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		// assertThat(actualResult.getMessage(), );
	}

	@Test
	public void getEnglishLastName() {
		Getname cmd = new Getname("getName", "name=l;country=en", "");
		
		Response actualResult = cmd.executeImpl(driver);

		assertEquals(actualResult.getCode(), Response.PASSED);
		// assertThat(actualResult.getMessage(), );
	}

}
