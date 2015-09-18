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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.UnderlineSpan;

import com.mevelopers.tabdilak.DecimalInputFilter;

import junit.framework.TestCase;

public class DecimalInputFilterTests extends TestCase {
	private com.mevelopers.tabdilak.DecimalInputFilter mDecimalInputFilter;
	public DecimalInputFilterTests() {
	
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		mDecimalInputFilter = new DecimalInputFilter();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	/* public void testAcceptsZeroLengthChanges() {
		//Zero length changes mean that we are deleting text
		fail("Not implemented yet!");
		
	} */
	
	public void testSpannableObjectsCheckedCorrectly() {
		SpannableStringBuilder str = new SpannableStringBuilder();
		str.append("123,456");
		str.setSpan(new UnderlineSpan(), 0, 7, 0);
		SpannableStringBuilder dest = new SpannableStringBuilder();
		String expected = "123,456";
		CharSequence actual = mDecimalInputFilter.filter((CharSequence) str, 0, 7, dest, 0, 7);
		
		assertEquals(expected, actual.toString());
		
		str.append("#$%");
		
	    actual = mDecimalInputFilter.filter((CharSequence) str, 0, 10, dest, 0, 10);
		
		assertEquals(expected, actual.toString());
		
	}
	
	public void testStringObjectsHandeledCorrectly() {
		StringBuilder str = new StringBuilder();
		str.append("123,456");
		SpannableStringBuilder dest = new SpannableStringBuilder();
		String expected = "123,456";
		CharSequence actual = mDecimalInputFilter.filter((CharSequence) str.toString(), 0, 7, dest, 0, 7);
		
		assertEquals(expected, actual.toString());
		
		str.append("#$%");
		
	    actual = mDecimalInputFilter.filter((CharSequence) str.toString(), 0, 10, dest, 0, 10);
		
		assertEquals(expected, actual.toString());
	}
	
	
	
	
}
