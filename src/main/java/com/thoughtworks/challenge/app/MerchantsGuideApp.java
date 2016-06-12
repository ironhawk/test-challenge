package com.thoughtworks.challenge.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.thoughtworks.challenge.interpreter.CommandInterpreter;
import com.thoughtworks.challenge.interpreter.commands.NumberQueryProcessingCommand;
import com.thoughtworks.challenge.interpreter.commands.PriceQueryProcessingCommand;
import com.thoughtworks.challenge.interpreter.commands.PricingStatementCommand;
import com.thoughtworks.challenge.interpreter.commands.RomanLiteralMappingCommand;

public class MerchantsGuideApp {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("missing argument!");
			System.out.println("usage: MerchantsGuideApp <inputFilePath>");
			System.exit(1);
		}

		List<String> commandLines = readInputFile(args[0]);

		CommandInterpreter interpreter = new CommandInterpreter(Arrays.asList( //
				new RomanLiteralMappingCommand(), //
				new PricingStatementCommand(), //
				new PriceQueryProcessingCommand(), //
				new NumberQueryProcessingCommand() //
		));

		interpreter.process(commandLines);
	}

	private static List<String> readInputFile(String filePath) {

		List<String> lines = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

			lines = stream.filter(line -> {
				return line.trim().length() > 0;
			}).collect(Collectors.toList());

		} catch (IOException e) {
			System.err.printf("failed to read file '%s', error: %s\n", filePath, e.getMessage());
		}

		return lines;
	}

}
