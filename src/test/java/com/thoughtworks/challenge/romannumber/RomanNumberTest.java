package com.thoughtworks.challenge.romannumber;

import org.junit.Assert;
import org.junit.Test;

public class RomanNumberTest {

	@Test
	public void trivialInvalidationTest() {
		expectIllegalArgumentException(null);
		expectIllegalArgumentException("  ");
		expectIllegalArgumentException("I I");
		expectIllegalArgumentException("IIIa");
	}

	@Test
	public void caseInsensitiveValidationTest() {
		expectIllegalArgumentException("i");
		expectIllegalArgumentException("v");
		expectIllegalArgumentException("x");
		expectIllegalArgumentException("l");
		expectIllegalArgumentException("c");
		expectIllegalArgumentException("d");
		expectIllegalArgumentException("m");
	}

	@Test
	public void repeatValidationTest() {

		new RomanNumber("I");
		new RomanNumber("II");
		new RomanNumber("III");
		expectIllegalArgumentException("IIII");

		new RomanNumber("X");
		new RomanNumber("XX");
		new RomanNumber("XXX");
		expectIllegalArgumentException("XXXX");
		new RomanNumber("XXXIX");

		new RomanNumber("C");
		new RomanNumber("CC");
		new RomanNumber("CCC");
		expectIllegalArgumentException("CCCC");
		new RomanNumber("CCCXC");

		new RomanNumber("M");
		new RomanNumber("MM");
		new RomanNumber("MMM");
		expectIllegalArgumentException("MMMM");
		new RomanNumber("MMMCM");

		new RomanNumber("D");
		expectIllegalArgumentException("DD");
		new RomanNumber("L");
		expectIllegalArgumentException("LL");
		new RomanNumber("V");
		expectIllegalArgumentException("VV");
	}

	@Test
	public void subtractValidationTest() {

		new RomanNumber("IV");
		new RomanNumber("IX");
		expectIllegalArgumentException("IL");
		expectIllegalArgumentException("ID");
		expectIllegalArgumentException("IM");
		expectIllegalArgumentException("IC");
		// only one small value might be subtracted
		expectIllegalArgumentException("IIV");
		expectIllegalArgumentException("IIX");

		new RomanNumber("XL");
		new RomanNumber("XC");
		expectIllegalArgumentException("XD");
		expectIllegalArgumentException("XM");
		// only one small value might be subtracted
		expectIllegalArgumentException("XXL");
		expectIllegalArgumentException("XXC");

		new RomanNumber("CD");
		new RomanNumber("CM");
		// only one small value might be subtracted
		expectIllegalArgumentException("CCD");
		expectIllegalArgumentException("CCM");

		expectIllegalArgumentException("VX");
		expectIllegalArgumentException("VL");
		expectIllegalArgumentException("VC");
		expectIllegalArgumentException("VD");
		expectIllegalArgumentException("VM");

		expectIllegalArgumentException("VX");
		expectIllegalArgumentException("VL");
		expectIllegalArgumentException("VC");
		expectIllegalArgumentException("VD");
		expectIllegalArgumentException("VM");

		expectIllegalArgumentException("LC");
		expectIllegalArgumentException("LD");
		expectIllegalArgumentException("LM");

		expectIllegalArgumentException("DM");
	}

	@Test
	public void decimalValueConversionTest() {

		// simple ones first
		Assert.assertEquals(1, new RomanNumber("I").getDecimalValue());
		Assert.assertEquals(2, new RomanNumber("II").getDecimalValue());
		Assert.assertEquals(3, new RomanNumber("III").getDecimalValue());
		Assert.assertEquals(5, new RomanNumber("V").getDecimalValue());
		Assert.assertEquals(10, new RomanNumber("X").getDecimalValue());
		Assert.assertEquals(20, new RomanNumber("XX").getDecimalValue());
		Assert.assertEquals(30, new RomanNumber("XXX").getDecimalValue());
		Assert.assertEquals(50, new RomanNumber("L").getDecimalValue());
		Assert.assertEquals(100, new RomanNumber("C").getDecimalValue());
		Assert.assertEquals(200, new RomanNumber("CC").getDecimalValue());
		Assert.assertEquals(300, new RomanNumber("CCC").getDecimalValue());
		Assert.assertEquals(500, new RomanNumber("D").getDecimalValue());
		Assert.assertEquals(1000, new RomanNumber("M").getDecimalValue());
		Assert.assertEquals(2000, new RomanNumber("MM").getDecimalValue());
		Assert.assertEquals(3000, new RomanNumber("MMM").getDecimalValue());

		// simple subtracts
		Assert.assertEquals(4, new RomanNumber("IV").getDecimalValue());
		Assert.assertEquals(9, new RomanNumber("IX").getDecimalValue());
		Assert.assertEquals(40, new RomanNumber("XL").getDecimalValue());
		Assert.assertEquals(90, new RomanNumber("XC").getDecimalValue());
		Assert.assertEquals(400, new RomanNumber("CD").getDecimalValue());
		Assert.assertEquals(900, new RomanNumber("CM").getDecimalValue());

		// and complex ones
		Assert.assertEquals(2939, new RomanNumber("MMCMXXXIX").getDecimalValue());
		Assert.assertEquals(3999, new RomanNumber("MMMCMXCIX").getDecimalValue());
	}

	private void expectIllegalArgumentException(String romanNumStr) {
		try {
			new RomanNumber(romanNumStr);
		} catch (IllegalArgumentException iae) {
			// System.out.println(romanNumStr + " error: " + iae.getMessage());
			return;
		} catch (Throwable t) {
			Assert.fail("we expected IllegalArgumentException for roman number '" + romanNumStr + "' but " + t.getClass() + " occured");
		}
		Assert.fail("we expected IllegalArgumentException for roman number '" + romanNumStr + "' but no exception occured");
	}

}
