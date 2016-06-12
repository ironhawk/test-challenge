package com.thoughtworks.challenge.interpreter.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.challenge.interpreter.CommandInterpreter.ProcessContext;
import com.thoughtworks.challenge.romannumber.RomanNumber;
import com.thoughtworks.challenge.romannumber.RomanNumberFactory;

/**
 * Processing command lines like "how much is pish tegj glob glob ?"
 * <p>
 * To answer the question it is using the word->RomanNumberLiteral mappinsg stored in
 * {@link ProcessContext}.
 * 
 * @author ironhawk
 *
 */
public class NumberQueryProcessingCommand implements Command {

	private static Pattern MATCHING_REGEX = Pattern.compile("^how much is[\\s]+([^\\?]+)\\?$", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean isEligible(String commandLine) {
		return MATCHING_REGEX.matcher(commandLine).matches();
	}

	@Override
	public void process(String commandLine, ProcessContext context) {
		Matcher m = MATCHING_REGEX.matcher(commandLine);
		m.matches();

		String quantityStr = m.group(1).trim();
		// this might fail here... but we should handle problems correctly so...
		try {
			RomanNumber quantity = RomanNumberFactory.getRomanNumber(quantityStr, context.getRomanNumberMapping());
			System.out.printf("%s is %s\n", quantityStr, quantity.getDecimalValue());
		} catch (IllegalArgumentException e) {
			System.err.printf("Sorry but I'm afraid I don't understand the quantity '%s' you provided... (at \"%s\") Error reason: %s\n", quantityStr, commandLine, e.getMessage());
		}

	}

}
