package com.thoughtworks.challenge.util;

/**
 * Since we can not use external libs however I like to use Apache Guava Preconditions here is a
 * very partial implementation of Preconditions util class.. :-)
 * 
 * @author ironhawk
 *
 */
public class Preconditions {

	private Preconditions() {
	}

	public static void checkState(boolean expectedState, String errMsg, Object... params) {
		if (!expectedState) {
			throw new IllegalStateException(String.format(errMsg, params));
		}
	}

	public static void checkArgument(boolean expectedArgumentState, String errMsg, Object... params) {
		if (!expectedArgumentState) {
			throw new IllegalArgumentException(String.format(errMsg, params));
		}
	}

}
