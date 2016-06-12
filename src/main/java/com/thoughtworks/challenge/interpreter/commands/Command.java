package com.thoughtworks.challenge.interpreter.commands;

import com.thoughtworks.challenge.interpreter.CommandInterpreter;

/**
 * Common interface for commandLine processors
 * 
 * @author ironhawk
 *
 */
public interface Command {

	/**
	 * The {@link CommandInterpreter} invokes this method to "ask" this command if it wants to
	 * process the given line or not
	 * 
	 * @param commandLine
	 *            the current line which is the subject of the question
	 * @return TRUE if we would like to process this line
	 */
	boolean isEligible(String commandLine);

	/**
	 * If this command is eligible for the given line {@link CommandInterpreter} will invoke this
	 * method for this command to let it do its job
	 * <p>
	 * The command can read / manipulate the context and/or print to output
	 * 
	 * @param context
	 */
	void process(String commandLine, CommandInterpreter.ProcessContext context);
}
