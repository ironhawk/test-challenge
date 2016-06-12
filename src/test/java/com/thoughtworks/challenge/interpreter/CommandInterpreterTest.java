package com.thoughtworks.challenge.interpreter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;

import com.thoughtworks.challenge.interpreter.commands.Command;

public class CommandInterpreterTest {

	@Test
	public void interpreterRunsEligibleCommandsTest() {

		// --- GIVEN

		// we have 3 commands
		Command command1 = mock(Command.class);
		Command command2 = mock(Command.class);
		Command command3 = mock(Command.class);

		String commandLine = "command";

		// 2 of them will say "I would like to process this command"
		when(command1.isEligible(commandLine)).thenReturn(true);
		when(command2.isEligible(commandLine)).thenReturn(false);
		when(command3.isEligible(commandLine)).thenReturn(true);

		// --- WHEN

		CommandInterpreter interpreter = new CommandInterpreter(Arrays.asList(command1, command2, command3));
		interpreter.process(Arrays.asList(commandLine));

		// --- THEN

		// interpreter asked all of them exactly 1 time
		verify(command1, times(1)).isEligible(commandLine);
		verify(command2, times(1)).isEligible(commandLine);
		verify(command3, times(1)).isEligible(commandLine);

		// but just 2 of them were invoked to process the command
		verify(command1, times(1)).process(eq(commandLine), any(CommandInterpreter.ProcessContext.class));
		verify(command2, never()).process(eq(commandLine), any(CommandInterpreter.ProcessContext.class));
		verify(command3, times(1)).process(eq(commandLine), any(CommandInterpreter.ProcessContext.class));
	}

}
