package com.thoughtworks.challenge.romannumber;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.thoughtworks.challenge.util.Preconditions;

/**
 * Static factory class to create a {@link RomanNumber} instances
 * 
 * @author ironhawk
 */
public class RomanNumberFactory {

	private RomanNumberFactory() {
	}

	/**
	 * Creates a {@link RomanNumber} instance from a valid Roman number string
	 * 
	 * @param romanNumberStr
	 *            sequence of {@link RomanNumberLiteral}s which satisfies the Roman number rules
	 * @throws IllegalArgumentException
	 */
	public static RomanNumber getRomanNumber(String romanNumberStr) throws IllegalArgumentException {
		return getRomanNumber(romanNumberStr, null);
	}

	/**
	 * Creates a {@link RomanNumber} instance from a string which could be transformed into a string
	 * contains a Roman number by removing all whitespaces and using the provided mapping
	 * <p>
	 * Example:<br>
	 * Assuming we have a mapping "grok" -> I, "prok" -> V<br>
	 * String "grok prok" will be transformed to "IV" and RomanNumber will be created accordingly
	 * 
	 * @param romanNumberStr
	 *            string which could be transformed into a string contains a Roman number
	 * @param mapping
	 *            maps words to {@link RomanNumberLiteral}s
	 * @throws IllegalArgumentException
	 */
	public static RomanNumber getRomanNumber(String romanNumberStr, Map<String, RomanNumberLiteral> mapping) throws IllegalArgumentException {
		Preconditions.checkArgument(romanNumberStr != null, "roman number string can not be NULL");

		String transformedString = mapping == null ? romanNumberStr : transformToStandardRomanNumberString(romanNumberStr, mapping);
		return new RomanNumber(transformedString);
	}

	private static String transformToStandardRomanNumberString(String romanNumberStr, final Map<String, RomanNumberLiteral> mapping) {
		List<String> words = Arrays.asList(romanNumberStr.trim().split("\\s+"));

		// let's do it with Java 8 stream based approach now...

		String[] transformedWords = words.stream().filter((s) -> {
			// skip empty strings - may come from leading/trailing whitespaces
			// of course we could avoid this step by simply trim() the original string which I would
			// do normaly - but I decided to do it this way for now...
			return s.length() > 0;
		}).map(word -> {
			// map the word - if we have mapping present, leave it as it is if not
			return mapping.get(word) == null ? word : mapping.get(word).toString();
		}).toArray(String[]::new);

		return String.join("", transformedWords);
	}

}
