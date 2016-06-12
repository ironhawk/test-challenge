package com.thoughtworks.challenge.romannumber;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.thoughtworks.challenge.util.Preconditions;

/**
 * Represents a roman number which can be instantiated from a roman number string using
 * RomanNumberFactory
 * 
 * @author ironhawk
 * 
 */
// @Immutable (we dont have this annotation now but this is true for this class)
public class RomanNumber {

	private final static Map<Character, Integer> values;

	/**
	 * To validate the roman number string has only allowed characters - in case not we would like
	 * to provide a separate exception message rather than just saying: invalid. This might help for
	 * the user to understand better what is wrong...
	 */
	private final static Pattern CHARACTER_VALIDATION_REGEX = Pattern.compile("^[IVXLCDM]+$");

	/**
	 * Validates the roman number string
	 * <p>
	 * This regex considers ordering as well as which roman literal might follow/precede which one
	 * and how many times they can be repeated
	 */
	private final static Pattern RULES_VALIDATION_REGEX = Pattern.compile("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");

	private final String numberStr;

	private final int decimalValue;

	static {
		values = new HashMap<>();
		values.put(RomanNumberLiteral.I.toChar(), 1);
		values.put(RomanNumberLiteral.V.toChar(), 5);
		values.put(RomanNumberLiteral.X.toChar(), 10);
		values.put(RomanNumberLiteral.L.toChar(), 50);
		values.put(RomanNumberLiteral.C.toChar(), 100);
		values.put(RomanNumberLiteral.D.toChar(), 500);
		values.put(RomanNumberLiteral.M.toChar(), 1000);
	}

	/**
	 * Use {@link RomanNumberFactory#getRomanNumber(String)} or
	 * {@link RomanNumberFactory#getRomanNumber(String, Map)} methods to instantiate this class
	 * 
	 * @param numberStr
	 *            sequence of {@link RomanNumberLiteral}s which satisfies Roman number rules as well
	 * @throws IllegalArgumentException
	 */
	protected RomanNumber(String numberStr) throws IllegalArgumentException {
		this.numberStr = numberStr;
		this.decimalValue = parse(numberStr);
	}

	public String getNumberStr() {
		return numberStr;
	}

	public long getDecimalValue() {
		return decimalValue;
	}

	/**
	 * @throws IllegalArgumentException
	 */
	private void preValidate(String romanNumStr) throws IllegalArgumentException {
		Preconditions.checkArgument(romanNumStr != null && romanNumStr.trim().length() > 0, "Roman number string can not be NULL or empty");
		Preconditions.checkArgument(CHARACTER_VALIDATION_REGEX.matcher(romanNumStr).matches(), "Roman number string '%s' contains invalid character(s)", romanNumStr);
		Preconditions.checkArgument(RULES_VALIDATION_REGEX.matcher(romanNumStr).matches(), "Roman number string '%s' is violating the rules", romanNumStr);
	}

	/**
	 * @throws IllegalArgumentException
	 */
	private int parse(String romanNumStr) throws IllegalArgumentException {

		preValidate(romanNumStr);

		int decimalValue = 0;
		int prevDigitValue = 0;

		for (int i = 0; i < romanNumStr.length(); i++) {
			int digitValue = values.get(romanNumStr.charAt(i));
			if (prevDigitValue < digitValue) {
				decimalValue -= 2 * prevDigitValue;
			}
			decimalValue += digitValue;
			prevDigitValue = digitValue;
		}

		return decimalValue;
	}

}
