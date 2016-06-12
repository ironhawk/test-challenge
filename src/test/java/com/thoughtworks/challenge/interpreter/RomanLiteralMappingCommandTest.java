package com.thoughtworks.challenge.interpreter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.challenge.interpreter.CommandInterpreter.ProcessContext;
import com.thoughtworks.challenge.interpreter.commands.RomanLiteralMappingCommand;
import com.thoughtworks.challenge.romannumber.RomanNumberLiteral;

public class RomanLiteralMappingCommandTest extends CommandTestBase {

	@Before
	public void setup() {
		command = new RomanLiteralMappingCommand();
	}

	@Test
	public void recognizedCommandLinesTest() {
		expectCommandLineWillTrigger("word1 is I");
		expectCommandLineWillTrigger("word1   is   I");

		// " is " is missing
		expectCommandLineWillNotTrigger("word1 I");
		// leading / trailing whitespaces
		expectCommandLineWillNotTrigger("  word1 is I");
		expectCommandLineWillNotTrigger("word1 is I  ");
		// more than 1 word
		expectCommandLineWillNotTrigger("word1 word2 is I");
		// invalid roman num literal
		expectCommandLineWillNotTrigger("word1 is K");
	}

	@Test
	public void contextModificationTest() {

		// --- GIVEN

		ProcessContext context = new ProcessContext();

		// --- WHEN

		command.process("word1 is I", context);
		command.process("word5 is V", context);
		command.process("word10 is X", context);
		command.process("word50 is L", context);
		command.process("word100 is C", context);
		command.process("word500 is D", context);
		command.process("word1000 is M", context);

		// --- THEN

		Assert.assertTrue(context.getRomanNumberMapping().get("word1") == RomanNumberLiteral.I);
		Assert.assertTrue(context.getRomanNumberMapping().get("word5") == RomanNumberLiteral.V);
		Assert.assertTrue(context.getRomanNumberMapping().get("word10") == RomanNumberLiteral.X);
		Assert.assertTrue(context.getRomanNumberMapping().get("word50") == RomanNumberLiteral.L);
		Assert.assertTrue(context.getRomanNumberMapping().get("word100") == RomanNumberLiteral.C);
		Assert.assertTrue(context.getRomanNumberMapping().get("word500") == RomanNumberLiteral.D);
		Assert.assertTrue(context.getRomanNumberMapping().get("word1000") == RomanNumberLiteral.M);

		// and prices are not touched
		Assert.assertTrue(context.getGoodsUnitPrices().isEmpty());
	}
}
