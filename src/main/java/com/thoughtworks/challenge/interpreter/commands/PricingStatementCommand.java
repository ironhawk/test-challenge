package com.thoughtworks.challenge.interpreter.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.challenge.interpreter.CommandInterpreter.ProcessContext;
import com.thoughtworks.challenge.romannumber.RomanNumber;
import com.thoughtworks.challenge.romannumber.RomanNumberFactory;

/**
 * Processing commands like "glob glob Silver is 34 Credits". It will store the unit price of the
 * given good in {@link ProcessContext}
 * 
 * @author ironhawk
 *
 */
public class PricingStatementCommand implements Command {

	private static Pattern MATCHING_REGEX = Pattern.compile("^(.+)[\\s]+([\\S]+)[\\s]+is[\\s]+([\\d]+)[\\s]+credits$", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean isEligible(String commandLine) {
		return MATCHING_REGEX.matcher(commandLine).matches();
	}

	@Override
	public void process(String commandLine, ProcessContext context) {
		Matcher m = MATCHING_REGEX.matcher(commandLine);
		m.matches();

		String quantityStr = m.group(1);
		String goodName = m.group(2);
		double price = Double.parseDouble(m.group(3));

		// this might fail here... but we should handle problems correctly so...
		try {
			RomanNumber quantity = RomanNumberFactory.getRomanNumber(quantityStr, context.getRomanNumberMapping());
			context.registerUnitPrice(goodName, price / quantity.getDecimalValue());
		} catch (IllegalArgumentException e) {
			System.err.printf("Sorry but I'm afraid I don't understand the quantity '%s' you provided... (at \"%s\") Error reason: %s\n", quantityStr, commandLine, e.getMessage());
		}
	}

}
