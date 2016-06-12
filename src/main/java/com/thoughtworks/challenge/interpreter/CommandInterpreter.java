package com.thoughtworks.challenge.interpreter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.challenge.interpreter.commands.Command;
import com.thoughtworks.challenge.romannumber.RomanNumberLiteral;

/**
 * A stateless command interpreter which is capable of processing the list of commands as input
 * 
 * @author ironhawk
 *
 */
public class CommandInterpreter {

	private final Collection<Command> supportedCommands;

	public CommandInterpreter(Collection<Command> supportedCommands) {
		this.supportedCommands = supportedCommands;
	}

	public void process(List<String> commandLines) {
		ProcessContext context = new ProcessContext();

		for (String line : commandLines) {
			if (!triggerCommands(line, context)) {
				// nobody recognized this line ...
				System.err.printf("I have no idea what you are talking about (by \"%s\")\n", line);
			}
		}
	}

	private boolean triggerCommands(String line, ProcessContext context) {
		boolean lineProcessed = false;
		for (Command command : supportedCommands) {
			if (command.isEligible(line)) {
				lineProcessed = true;
				command.process(line, context);
			}
		}
		return lineProcessed;
	}

	/**
	 * A very simple context object containing all stuff which could be manipulated by
	 * {@link Command}s during input processing done by the interpreter
	 * 
	 * @author ironhawk
	 */
	public static class ProcessContext {

		/**
		 * Contains word -> RomanNumberLiteral mapping the interpreter works with
		 */
		private final Map<String, RomanNumberLiteral> romanNumberMapping;

		/**
		 * Stores the unit price of different goods as instructed by commands
		 */
		private final Map<String, Double> goodsUnitPrices;

		protected ProcessContext() {
			romanNumberMapping = new HashMap<>();
			goodsUnitPrices = new HashMap<>();
		}

		/**
		 * @return a copy of the current word -> RomanNumberLiteral mapping
		 */
		public Map<String, RomanNumberLiteral> getRomanNumberMapping() {
			return new HashMap<>(romanNumberMapping);
		}

		/**
		 * Registers a word -> RomanNumberLiteral mapping in the context
		 * 
		 * @param word
		 *            the word you want to use as a Roman number literal
		 * @param romanNumberLiteral
		 *            the Roman number we want to map with the word
		 */
		public void registerRomanNumberLiteralMapping(String word, RomanNumberLiteral romanNumberLiteral) {
			romanNumberMapping.put(word, romanNumberLiteral);
		}

		/**
		 * Registers the unit price of the given good
		 */
		public void registerUnitPrice(String goodName, double unitPrice) {
			goodsUnitPrices.put(goodName, unitPrice);
		}

		/**
		 * Returns the registered unit price of the given good
		 * 
		 * @param goodName
		 * @return the unit price or NULL if this good is not registered
		 */
		public Double getUnitPriceOf(String goodName) {
			return goodsUnitPrices.get(goodName);
		}

		// @VisibleForTesting - we dont have this annotation because it is in Guava lib
		protected Map<String, Double> getGoodsUnitPrices() {
			// we just return a copy to make sure internal map is not modified
			return new HashMap<>(goodsUnitPrices);
		}

	}
}
