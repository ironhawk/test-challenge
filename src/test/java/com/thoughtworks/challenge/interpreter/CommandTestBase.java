package com.thoughtworks.challenge.interpreter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Before;

import com.thoughtworks.challenge.interpreter.commands.Command;

/**
 * Just contains a couple of common useful methods for testing {@link Command}s. It is also
 * replacing std in/out to make it testable
 * 
 * @author ironhawk
 *
 */
public class CommandTestBase {

	protected Command command;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void mockOutOutputs() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	public ByteArrayOutputStream getOutContent() {
		return outContent;
	}

	public ByteArrayOutputStream getErrContent() {
		return errContent;
	}

	protected void expectCommandLineWillTrigger(String commandLine) {
		Assert.assertTrue("commandLine '" + commandLine + "' should have been recognized", command.isEligible(commandLine));
	}

	protected void expectCommandLineWillNotTrigger(String commandLine) {
		Assert.assertFalse("commandLine '" + commandLine + "' should have NOT been recognized", command.isEligible(commandLine));
	}

}
