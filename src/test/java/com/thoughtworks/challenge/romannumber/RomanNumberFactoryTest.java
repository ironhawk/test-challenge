package com.thoughtworks.challenge.romannumber;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class RomanNumberFactoryTest {

	private Map<String, RomanNumberLiteral> getTestMapping() {
		Map<String, RomanNumberLiteral> mapping = new HashMap<>();
		mapping.put("grok", RomanNumberLiteral.I);
		mapping.put("prok", RomanNumberLiteral.V);
		mapping.put("pish", RomanNumberLiteral.X);
		mapping.put("tegj", RomanNumberLiteral.L);
		return mapping;
	}

	@Test
	public void emptyInputTest() {
		Map<String, RomanNumberLiteral> mapping = getTestMapping();

		expectIllegalArgumentException(null, mapping);
		expectIllegalArgumentException(null, null);

		expectIllegalArgumentException("  ", mapping);
		expectIllegalArgumentException("  ", null);
	}

	@Test
	public void simpleMappingTest() {
		Map<String, RomanNumberLiteral> mapping = getTestMapping();

		Assert.assertEquals(2, RomanNumberFactory.getRomanNumber("grok grok", mapping).getDecimalValue());
		Assert.assertEquals(14, RomanNumberFactory.getRomanNumber("pish grok prok", mapping).getDecimalValue());
		Assert.assertEquals(14, RomanNumberFactory.getRomanNumber("pish grok prok", mapping).getDecimalValue());

		// normal Roman numbers should still behave as usual - regardless presence of the mapping
		Assert.assertEquals(2939, RomanNumberFactory.getRomanNumber("MMCMXXXIX", mapping).getDecimalValue());
		Assert.assertEquals(3999, RomanNumberFactory.getRomanNumber("MMMCMXCIX", mapping).getDecimalValue());
	}

	@Test
	public void mappingWithWhitespaceHandlingTest() {
		Map<String, RomanNumberLiteral> mapping = getTestMapping();

		// whitespaces should be filtered out
		Assert.assertEquals(14, RomanNumberFactory.getRomanNumber(" pish    grok  prok   ", mapping).getDecimalValue());

		// as a consequence of whitespace handling roman number which is split into more words
		// should be treated as the Roman number without whitespaces
		Assert.assertEquals(2939, RomanNumberFactory.getRomanNumber(" MM CM   XX XIX   ", mapping).getDecimalValue());
	}

	@Test
	public void moreComplexMappingTest() {
		Map<String, RomanNumberLiteral> mapping = getTestMapping();

		// normal RomanNumberLiterals should still be considered as part of the number
		Assert.assertEquals(22, RomanNumberFactory.getRomanNumber("XX grok grok", mapping).getDecimalValue());
		Assert.assertEquals(1614, RomanNumberFactory.getRomanNumber("M D  C pish grok prok", mapping).getDecimalValue());

		// unknown word should result in a failure
		expectIllegalArgumentException("grok word prok", mapping);
		// or even non-whitespace symbol...
		expectIllegalArgumentException("grok, prok", mapping);
	}

	private void expectIllegalArgumentException(String romanNumStr, Map<String, RomanNumberLiteral> mapping) {
		try {
			if (mapping != null)
				RomanNumberFactory.getRomanNumber(romanNumStr, mapping);
			else
				RomanNumberFactory.getRomanNumber(romanNumStr);
		} catch (IllegalArgumentException iae) {
			return;
		} catch (Throwable t) {
			Assert.fail("we expected IllegalArgumentException for roman number '" + romanNumStr + "' but " + t.getClass() + " occured");
		}
		Assert.fail("we expected IllegalArgumentException for roman number '" + romanNumStr + "' but no exception occured");
	}

}
