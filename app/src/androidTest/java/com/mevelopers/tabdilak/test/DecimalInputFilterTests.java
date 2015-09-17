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
