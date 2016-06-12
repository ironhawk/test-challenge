package com.thoughtworks.challenge.romannumber;

/**
 * Roman number digits
 * 
 * @author ironhawk
 */
public enum RomanNumberLiteral {
	I, V, X, L, C, D, M;

	public Character toChar() {
		return this.toString().charAt(0);
	}
}
