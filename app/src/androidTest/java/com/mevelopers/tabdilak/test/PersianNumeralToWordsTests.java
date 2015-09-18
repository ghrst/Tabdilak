/*
 Copyright: 2015 Gholamreza Sabery Tabrizy

  This file is part of Tabdilak.

    Tabdilak is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Tabdilak is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Tabdilak.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mevelopers.tabdilak.test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mevelopers.tabdilak.PersianNumeralToWords;

public class PersianNumeralToWordsTests extends TestCase {

	public PersianNumeralToWordsTests() {
		this("PersianNumeralToWordsTests");
	}
	
	public PersianNumeralToWordsTests(String name) {
		super(name);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testNumberToStringShoulThrowException() {
		try {
			PersianNumeralToWords.numberToString(1234567890123L);
			fail("NumberFormatException from numberToString method does not thrown!!!");
		} catch(Exception e) {
			// Do nothing here
		}
	}

	@Test
	public void testNumberToString() {
		String results = PersianNumeralToWords.numberToString(0);
		assertEquals("صفر",results);
		
	    results = PersianNumeralToWords.numberToString(1000000000);
		assertEquals("یک میلیارد",results);
		
		results = PersianNumeralToWords.numberToString(100000000000L);
		assertEquals("یکصد میلیارد",results);
		
		results = PersianNumeralToWords.numberToString(900000000000L);
		assertEquals("نهصد میلیارد",results);
		
		results = PersianNumeralToWords.numberToString(900241000000L);
		assertEquals("نهصد میلیارد و دویست و چهل و یک میلیون",results);
		
		results = PersianNumeralToWords.numberToString(900241325000L);
		assertEquals("نهصد میلیارد و دویست و چهل و یک میلیون و سیصد و بیست و پنج هزار",results);
		
		results = PersianNumeralToWords.numberToString(300);
		assertEquals("سیصد",results);
		
		results = PersianNumeralToWords.numberToString(1);
		assertEquals("یک",results);
		
		results = PersianNumeralToWords.numberToString(123);
		assertEquals("یکصد و بیست و سه",results);
		
		results = PersianNumeralToWords.numberToString(1300);
		assertEquals("یک هزار و سیصد",results);
		
		results = PersianNumeralToWords.numberToString(1598);
		assertEquals("یک هزار و پانصد و نود و هشت",results);
		
		results = PersianNumeralToWords.numberToString(1000001);
		assertEquals("یک میلیون و یک",results);
		
		results = PersianNumeralToWords.numberToString(6001);
		assertEquals("شش هزار و یک",results);
		
		/* A zero prefix in most programming languages means an Octal number so this line must produce
		 * 384
		 */
		results = PersianNumeralToWords.numberToString(0600);
		assertEquals("سیصد و هشتاد و چهار",results);
		
		//Sign of numbers must be ignored by our conversion function.
		results = PersianNumeralToWords.numberToString(-600);
		assertEquals("ششصد", results);
		
		results = PersianNumeralToWords.numberToString(12333333330L);
		assertEquals("دوازده میلیارد و سیصد و سی و سه میلیون و سیصد و سی و سه هزار و سیصد و سی", results);
		
		results = PersianNumeralToWords.numberToString(1234567890L);
		assertEquals("یک میلیارد و دویست و سی و چهار میلیون و پانصد و شصت و هفت هزار و هشتصد و نود", results);
		
		
		
	}

}
