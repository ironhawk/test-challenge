package com.thoughtworks.challenge.interpreter.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.challenge.interpreter.CommandInterpreter.ProcessContext;
import com.thoughtworks.challenge.romannumber.RomanNumber;
import com.thoughtworks.challenge.romannumber.RomanNumberFactory;

/**
 * Processing command lines like "how many Credits is glob prok Silver ?"
 * <p>
 * To answer the question it is using the word->RomanNumberLiteral mappinsg and unit prices of goods
 * stored in {@link ProcessContext}.
 * 
 * @author ironhawk
 *
 */
public class PriceQueryProcessingCommand implements Command {

	private static Pattern MATCHING_REGEX = Pattern.compile("^how many credits is[\\s]+(.+)[\\s]+([\\S]+)[\\s]+\\?$", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean isEligible(String commandLine) {
		return MATCHING_REGEX.matcher(commandLine).matches();
	}

	@Override
	public void process(String commandLine, ProcessContext context) {
		Matcher m = MATCHING_REGEX.matcher(commandLine);
		m.matches();

		String quantityStr = m.group(1).trim();
		String goodName = m.group(2);

		Double unitPrice = context.getUnitPriceOf(goodName);
		if (unitPrice == null) {
			// looks we don't have this price stored...
			System.err.printf("Sorry but I'm afraid I don't have price information for '%s'... (at \"%s\")\n", goodName, commandLine);
		} else {
			// this might fail here as well... but we should handle problems correctly so...
			try {
				RomanNumber quantity = RomanNumberFactory.getRomanNumber(quantityStr, context.getRomanNumberMapping());
				long price = Math.round(unitPrice * quantity.getDecimalValue());
				System.out.printf("%s %s is %s Credits\n", quantityStr, goodName, price);
			} catch (IllegalArgumentException e) {
				System.err.printf("Sorry but I'm afraid I don't understand the quantity '%s' you provided... (at \"%s\")\n", quantityStr, commandLine);
			}
		}
	}

}
