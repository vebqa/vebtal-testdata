package org.vebqa.vebtal.td.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.td.IBANDriver;

public class ReadRecordTest {

	@Rule
	public final IBANDriver driver = new IBANDriver().setDataPath("./resources/blz-test-txt-data.txt").load();

	@Test
	public void readDataAndCheckCount() {
		assertEquals(1, driver.getRecordCount());
	}
	
	@Test
	public void readDataAndSearchBICbyBLZ() {
		assertEquals("ENTBKAA1100", driver.getBICbyBLZ("10000002"));
	}
	
}
