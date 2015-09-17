package com.mevelopers.tabdilak.test;

import static junit.framework.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.mevelopers.tabdilak.PersianUtils;

public class PersianUtilsTests {
	private static final char [] PERSIAN_NUMERALS = {'\u06F0', '\u06F1', '\u06F2', '\u06F3', '\u06F4','\u06F5',
		'\u06F6', '\u06F7', '\u06F8', '\u06F9'};
	public PersianUtilsTests() {
		
	}
	
	@Before
	public void setUp() {
		
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testIfConvertoEnglishNumeralToPersianRaisesExceptionWithIllegalArguments() {
		String [] inputs = {"123a45", "abcdef", "�ȁ�", null, ""};
		for(int i=0; i< inputs.length; i++) {
			try {
				PersianUtils.convertEnglishNumeralToPersian(inputs[i]);
			} catch(IllegalArgumentException e) {
				continue;
			}
			fail("Exception not thrown with input: " + inputs[i]);
		}
	}
	
	@Test
	public void testConvertEnglishNumeralToPersianWithSingleDigits() {
		String actual;
		for(int i=0;i<=9;i++) {
			actual = PersianUtils.convertEnglishNumeralToPersian(Integer.toString(i));
			assertEquals(String.valueOf(PERSIAN_NUMERALS[i]), actual);
		}
	}
	
	@Test
	public void testConvertEnglishNumeralToPersian() {
		String expected, actual, englishNumber;
		Random randGenerator = new Random();
		int index;
		for (int i=0; i<=1000; i++) {
			expected = "";
			englishNumber = "";
			for(int j=0; j<=12;j++) {
				index = randGenerator.nextInt(10);
				englishNumber += index;
				expected += PERSIAN_NUMERALS[index];
			}
			actual = PersianUtils.convertEnglishNumeralToPersian(englishNumber);
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void testConvertPersianNumeralToEnglishRaisesExceptionWithIllegalArguments() {
		String [] inputs = {"�ȁ�", null, "", "\u06F0abȁ\u06F5"};
		for(int i=0; i< inputs.length; i++) {
			try {
				PersianUtils.convertPersianNumeralToEnglish(inputs[i]);
			} catch(IllegalArgumentException e) {
				continue;
			}
			fail("Exception not thrown with input: " + inputs[i]);
		}
	}
	
	@Test
	public void testConvertPersianNumeralToEnglishWithSingleDigits() {
		String actual;
		for(int i=0; i<PERSIAN_NUMERALS.length;i++) {
			actual = PersianUtils.convertPersianNumeralToEnglish(String.valueOf(PERSIAN_NUMERALS[i]));
			assertEquals(String.valueOf(i), actual);
		}
	}
	
	@Test
	public void testConvertPersianNumeralToEnglish() {
		String expected, actual, persianNumber;
		Random randGenerator = new Random();
		int index;
		for (int i=0; i<=1000; i++) {
			expected = "";
			persianNumber = "";
			for(int j=0; j<=12;j++) {
				index = randGenerator.nextInt(10);
				persianNumber += PERSIAN_NUMERALS[index];
				expected += String.valueOf(index);
			}
			actual = PersianUtils.convertPersianNumeralToEnglish(persianNumber);
			assertEquals(expected, actual);
		}
	}
	
	

	
}
