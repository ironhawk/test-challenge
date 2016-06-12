package com.thoughtworks.challenge.interpreter.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.challenge.interpreter.CommandInterpreter.ProcessContext;
import com.thoughtworks.challenge.romannumber.RomanNumberLiteral;

/**
 * Processes command lines like "glob is I". It creates a word -> RomanNumberLiteral mapping in
 * {@link ProcessContext}
 * 
 * @author ironhawk
 *
 */
public class RomanLiteralMappingCommand implements Command {

	private static Pattern MATCHING_REGEX = Pattern.compile("^([\\S]+)[\\s]+is[\\s]+([IVXLCDM]{1})$", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean isEligible(String commandLine) {
		return MATCHING_REGEX.matcher(commandLine).matches();
	}

	@Override
	public void process(String commandLine, ProcessContext context) {
		Matcher m = MATCHING_REGEX.matcher(commandLine);
		m.matches();
		String word = m.group(1).trim();
		RomanNumberLiteral romanNumberLiteral = RomanNumberLiteral.valueOf(m.group(2));
		context.registerRomanNumberLiteralMapping(word, romanNumberLiteral);
	}

}
