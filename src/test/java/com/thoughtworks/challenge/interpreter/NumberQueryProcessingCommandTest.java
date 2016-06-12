package com.thoughtworks.challenge.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.challenge.interpreter.CommandInterpreter.ProcessContext;
import com.thoughtworks.challenge.interpreter.commands.NumberQueryProcessingCommand;
import com.thoughtworks.challenge.romannumber.RomanNumberLiteral;

public class NumberQueryProcessingCommandTest extends CommandTestBase {

	@Before
	public void setup() {
		command = new NumberQueryProcessingCommand();
	}

	@Test
	public void recognizedCommandLinesTest() {
		expectCommandLineWillTrigger("how much is romanNumberWord1 romanNumberWord2 ?");
		expectCommandLineWillTrigger("how much is romanNumberWord1 romanNumberWord2 romanNumberWord3 romanNumberWord4 ?");
		// inner whitespaces should not be a problem also case sensitivity
		expectCommandLineWillTrigger("HOW MUch is     romanNumberWord1    romanNumberWord2    ?");

		// wrong leading
		expectCommandLineWillNotTrigger("how many is romanNumberWord1 romanNumberWord2 ?");
		// "?" is missing
		expectCommandLineWillNotTrigger("how much is romanNumberWord1 romanNumberWord2");
		// there is no any romanNumberWord1
		expectCommandLineWillNotTrigger("how much is ?");
	}

	@Test
	public void queryTest() {

		// --- GIVEN

		ProcessContext context = new ProcessContext();
		context.registerRomanNumberLiteralMapping("glob", RomanNumberLiteral.I);
		context.registerRomanNumberLiteralMapping("prok", RomanNumberLiteral.V);
		context.registerRomanNumberLiteralMapping("pish", RomanNumberLiteral.X);
		context.registerRomanNumberLiteralMapping("tegj", RomanNumberLiteral.L);

		// --- WHEN

		command.process("how much is pish tegj glob glob ?", context);
		// play in an invalid number query
		command.process("how much is qqq tegj glob glob ?", context);

		// --- THEN

		Assert.assertEquals("pish tegj glob glob is 42\n", getOutContent().toString());
		Assert.assertTrue(getErrContent().toString().startsWith("Sorry but I'm afraid I don't understand the quantity"));

		// and RomanNumber mapping in the context is not touched
		Map<String, RomanNumberLiteral> expectedMapping = new HashMap<>();
		expectedMapping.put("glob", RomanNumberLiteral.I);
		expectedMapping.put("prok", RomanNumberLiteral.V);
		expectedMapping.put("pish", RomanNumberLiteral.X);
		expectedMapping.put("tegj", RomanNumberLiteral.L);
		Assert.assertEquals(expectedMapping, context.getRomanNumberMapping());

		// and unit prices in context not touched
		Assert.assertTrue(context.getGoodsUnitPrices().isEmpty());

	}
}
