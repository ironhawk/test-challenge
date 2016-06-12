package com.thoughtworks.challenge.interpreter;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.challenge.interpreter.CommandInterpreter.ProcessContext;
import com.thoughtworks.challenge.interpreter.commands.PricingStatementCommand;
import com.thoughtworks.challenge.romannumber.RomanNumberLiteral;

public class PricingStatementCommandTest extends CommandTestBase {

	@Before
	public void setup() {
		command = new PricingStatementCommand();
	}

	@Test
	public void recognizedCommandLinesTest() {
		expectCommandLineWillTrigger("romanNumberWord1 romanNumberWord2 nameOfGood is 34 Credits");
		expectCommandLineWillTrigger("romanNumberWord1 romanNumberWord2 romanNumberWord3 nameOfGood is 34 Credits");
		// inner whitespaces should not be a problem also case sensitivity (note: 'Credits' is
		// 'credits'
		// here)
		expectCommandLineWillTrigger("romanNumberWord1    romanNumberWord2  romanNumberWord3    nameOfGood    is    34   credits");

		// " is " is missing
		expectCommandLineWillNotTrigger("romanNumberWord1 romanNumberWord2 nameOfGood 34 Credits");
		// "Credits" has typo
		expectCommandLineWillNotTrigger("romanNumberWord1 romanNumberWord2 nameOfGood is 34 Credit");
		// where we expect price that is not a valid number
		expectCommandLineWillNotTrigger("romanNumberWord1 romanNumberWord2 nameOfGood is 3A Credits");
	}

	@Test
	public void contextModificationTest() {

		// --- GIVEN

		ProcessContext context = new ProcessContext();
		context.registerRomanNumberLiteralMapping("glob", RomanNumberLiteral.I);
		context.registerRomanNumberLiteralMapping("prok", RomanNumberLiteral.V);
		context.registerRomanNumberLiteralMapping("pish", RomanNumberLiteral.X);
		context.registerRomanNumberLiteralMapping("tegj", RomanNumberLiteral.L);

		// --- WHEN

		command.process("glob glob Silver is 34 Credits", context);
		command.process("glob prok Gold is 57800 Credits", context);

		command.process("pish pish Iron is 3910 Credits", context);
		// and let's overwrite now
		command.process("pish prok Iron is 1500 Credits", context);

		// send in a command with invalid Roman number in it
		command.process("pish blabla Iron is 1500 Credits", context);

		// --- THEN

		// nothing appeared on std out
		Assert.assertEquals("", getOutContent().toString());
		// but we have on std err
		Assert.assertTrue(getErrContent().toString().startsWith("Sorry but I'm afraid I don't understand the quantity"));

		// we have the unit prices stored
		Assert.assertEquals(new Double(17), context.getUnitPriceOf("Silver"));
		Assert.assertEquals(new Double(14450), context.getUnitPriceOf("Gold"));
		Assert.assertEquals(new Double(100), context.getUnitPriceOf("Iron"));

		// we have just these unit prices
		Set<String> expectedKeySet = new HashSet<>();
		Collections.addAll(expectedKeySet, "Silver", "Gold", "Iron");
		Assert.assertEquals(expectedKeySet, context.getGoodsUnitPrices().keySet());

		// and RomanNumber mapping in the context is not touched
		Map<String, RomanNumberLiteral> expectedMapping = new HashMap<>();
		expectedMapping.put("glob", RomanNumberLiteral.I);
		expectedMapping.put("prok", RomanNumberLiteral.V);
		expectedMapping.put("pish", RomanNumberLiteral.X);
		expectedMapping.put("tegj", RomanNumberLiteral.L);
		Assert.assertEquals(expectedMapping, context.getRomanNumberMapping());
	}
}
