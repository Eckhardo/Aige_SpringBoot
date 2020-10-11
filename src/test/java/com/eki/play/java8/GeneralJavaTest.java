package com.eki.play.java8;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ekirschning
 *
 */

public class GeneralJavaTest {
	Logger logger = LoggerFactory.getLogger(GeneralJavaTest.class);

	/**
	 * logger.debug() with DOUBLE QUOTES : String
	 */
	@Test
	public void predefinedMethodInJava() {
		// String Literal
		logger.debug("Hello Java");

		logger.debug("Hello \"Java\" ");
		logger.debug("Hello \n Java");
		logger.debug("Hello\nJava");
		logger.debug("Hello\tJava");
		// expression
		logger.debug("(2 * 5)");

		// String Literal
		logger.debug("2 * 5");

		// Concatenation of String Literals and an Expression
		logger.debug("Current Year" + ": " + 2 * 1009);
	}

	/**
	 * Same String reference, taken from the String cache (PermGen)
	 */
	@Test
	public void checkCachedStringsForEqualness() {
		String string1 = "Hello";
		String string2 = "Hello";

		assertTrue(string1.equals(string2));
		assertTrue(string1 == string2);
	}

	/**
	 * Equal Strings , 2 instances;
	 */
	@Test
	public void checkInstantiatedStringsForEqualness() {
		String string1 = new String("Hello");
		String string2 = new String("Hello");

		assertTrue(string1.equals(string2));
		assertFalse(string1 == string2);
	}

	/**
	 * Devide Integer values.
	 */
	@Test
	public void devideIntegers() {
		int five = 5;
		int two = 2;

		assertTrue(five / two == 2);
	}

	/**
	 * Devide Decimal values (Floating Point values).
	 */
	@Test
	public void devideDecimals() {
		double five = 5.0;
		double two = 2.0;

		assertTrue(five / two == 2.5);
	}

	/**
	 * Precedence *,/ and % > + and -.
	 */
	@Test
	public void mathOperatorsPrecedence() {
		int five = 5;
		int two = 2;
		int six = 6;

		logger.debug(System.getenv().toString());
		assertTrue(five * two + six == 16);
		assertTrue(five * (two + six) == 40);
	}

	/**
	*  Test for unmodifiableList;
	 */
	@Test
	public void unmodifiableListTest() {
		List<Integer> modifiedList = new ArrayList<>();
		modifiedList.add(1);

		List<Integer> unmodifiableList = Collections.unmodifiableList(modifiedList);
		
		boolean exceptionThrown = false;
		try {
			unmodifiableList.add(2);
		} catch (UnsupportedOperationException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		
		exceptionThrown = false;
		try {
			unmodifiableList.set(0,2);
		} catch (UnsupportedOperationException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	
	}
}
